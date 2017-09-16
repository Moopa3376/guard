package net.moopa3376.guard.api;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.moopa3376.guard.Guard;
import net.moopa3376.guard.annotation.api.ApiHttpMethod;
import net.moopa3376.guard.annotation.api.ApiHttpParam;
import net.moopa3376.guard.annotation.api.ApiHttpPath;
import net.moopa3376.guard.common.LoadClasses;
import net.moopa3376.guard.config.GuardConfigs;
import net.moopa3376.guard.http.HttpRequestMethod;
import net.moopa3376.guard.http.HttpRequestParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Moopa on 10/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class ApiInfoMngt {
    private static Logger logger = LoggerFactory.getLogger(ApiInfoMngt.class);
    private static Map<String,Api> apis = new HashMap<String, Api>();

    /**
     * 用于初始化ApiInfoMngt
     * 1. 根据配置下的名录来收集所有api信息
     */
    public static void init() {
        //首先根据配置文件读取需要扫描的包名
        String packages = GuardConfigs.get("package.scan");

        if(packages == null){
            logger.info("guard config - package.scan not set correctly.");
        }

        String[] ss = packages.split(",");

        for(String s : ss){
            //根据配置找到相应classes
            boolean output_api = "true".equals(GuardConfigs.get("output.api"));
            List<Class<?>> classes = LoadClasses.getClassByPkgName(s,true,Thread.currentThread().getContextClassLoader());

            if(output_api){
                logger.info("find package : {}, class num : {}.",s,classes.size());
            }

            for(Class<?> c : classes){
                if(output_api){
                    logger.info("find class : {}",c.getName());
                }

                Method[] methods = c.getMethods();

                RequestMapping requestMapping = c.getAnnotation(RequestMapping.class);
                String ahead_request_path = requestMapping != null ? Arrays.toString(requestMapping.value()) + (Arrays.toString(requestMapping.value()).toString().endsWith("/]") ? "" :  "/") : "";

                for(Method m : methods){
                    Api api = new Api();

                    /**开始在当前method上寻找被配置了apc的方法**/
                    //request path
                    ApiHttpPath path = m.getAnnotation(ApiHttpPath.class);
                    if(path == null){
                        continue;
                    }
                    String requestPath = ahead_request_path + path.requestPath();
                    if(requestPath == null || requestPath.length() == 0){
                        logger.error("Method {} in class {} don't have correct request path.", m.getName(), c.getName());
                        continue;
                    }

                    api.setRequestPath(path.requestPath());

                    //http method
                    ApiHttpMethod method = m.getAnnotation(ApiHttpMethod.class);
                    if(method == null){
                        logger.warn("Method {} - Path {}  use default http method : GET.",m.getName(),requestPath);
                        api.setRequestMethod(HttpRequestMethod.GET);
                    }else {
                        api.setRequestMethod(method.httpMethod());
                    }

                    //http params
                    ApiHttpParam params = m.getAnnotation(ApiHttpParam.class);
                    if(params == null){
                        logger.warn("Method {} - Path {} ignored with no params.",m.getName(),requestPath);
                        continue;
                    }

                    String s_params = params.params();
                    Map<String,HttpRequestParameter> maps = new HashMap<String, HttpRequestParameter>();
                    //利用gson进行解析
                    boolean parserFail = false;
                    try{
                        JsonParser jsonParser = new JsonParser();
                        JsonArray jsonArray = (JsonArray)jsonParser.parse(s_params);

                        if(jsonArray.size() == 0){
                            logger.warn("Method {} - Path {} ignored with no params.",m.getName(),requestPath);
                            continue;
                        }

                        for(JsonElement jsonElement : jsonArray){
                            JsonObject jsonObject = (JsonObject)jsonElement;

                            JsonElement jsonElement_key = jsonObject.get("key");
                            String key = null;
                            if(jsonElement_key == null){
                                logger.error("Method {} - Path {} params setting error - some key is not set",m.getName(),requestPath);
                                continue;
                            }
                            key = jsonElement_key.getAsString();
                            if(key.length() == 0){
                                logger.error("Method {} - Path {} params setting error - some key is illegal",m.getName(),requestPath);
                                continue;
                            }



                            JsonElement jsonElement_pattern = jsonObject.get("pattern");
                            String pattern = null;
                            if(jsonElement_pattern == null){
                                pattern = ".*";
                            }else{
                                pattern = jsonElement_pattern.getAsString();
                            }



                            JsonElement jsonElement_required = jsonObject.get("required");
                            boolean required = true;
                            if(jsonElement_required != null){
                                required = Boolean.valueOf(jsonElement_required.getAsString());
                            }else {
                                logger.warn("Method {} - Path {} params required invalid, set default value - true",m.getName(),requestPath);
                            }

                            //加入Map
                            HttpRequestParameter httpRequestParameter = new HttpRequestParameter();
                            httpRequestParameter.setPattern(Pattern.compile(pattern));
                            httpRequestParameter.setRequired(Boolean.valueOf(required));
                            maps.put(key,httpRequestParameter);

                        }
                    }catch (Exception e){
                        logger.error("Method {} Path {} params setting error.",m.getName(),requestPath);
                        logger.error(e.getMessage());
                        parserFail= true;
                    }

                    if(parserFail){
                        continue;
                    }

                    api.setParams(maps);
                    //将此解析好的api放进缓存中
                    apis.put(Guard.guardService.apiNameDefinetion(requestPath,api.requestMethod),api);

                    if(output_api){
                        logger.info("Resolve api request path : {} method: {} successfully.",api.getRequestPath(),api.getRequestMethod().getMethodName());
                    }

                }
            }

        }
    }

    public static Api getApi(String path, HttpRequestMethod method){
        return apis.get(Guard.guardService.apiNameDefinetion(path,method));
    }

}
