--
-- 角色表
--
CREATE TABLE `eat`.`role` (
  `id`     INT(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `rolename` VARCHAR(32)      NOT NULL,
  `description` VARCHAR(128) NOT NULL ,
  `gmt_create` DATE NOT NULL ,
  `gmt_modified` DATE NOT NULL ,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


--
-- 角色无关的权限表
--
CREATE TABLE `eat`.`permission`(
  `id` INT(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `permission_identifier` VARCHAR(128) NOT NULL COMMENT '用来标识该permission,一般采用url+Http  method的方式',
  `description` VARCHAR(64) NOT NULL ,
  `gmt_create` DATE NOT NULL ,
  `gmt_modified` DATE NOT NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


--
-- 将角色和权限关联起来的表
--
CREATE TABLE `eat`.`role_permission`(
  `role_permission_id` INT(32) NOT NULL AUTO_INCREMENT,
  `id` INT(32) NOT NULL,
  `id` INT(32) NOT NULL,
  `gmt_create` DATE NOT NULL ,
  PRIMARY KEY (`role_permission_id`)
)ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


--
-- 账户基本信息
--
CREATE TABLE `eat`.`account_basicinfo` (
  `id`   BIGINT(64) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(64)         NOT NULL,
  `password`     VARCHAR(128)         NOT NULL,
  `phone`        VARCHAR(20)        NOT NULL ,
  `gmt_create` DATE            NOT NULL,
  `gmt_modified` DATE NOT NULL ,
  `id`      INT(32) UNSIGNED    NOT NULL,
  `status` TINYINT(4) UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`name`)
)
  ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '这是用户基本信息表，里面存放的都是一些不经常改变的属性域。';