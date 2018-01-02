

CREATE TABLE IF NOT EXISTS `m1_common_version` (
`oid`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '流水号' ,
`type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'PROJECT' COMMENT '数据库类型' ,
`version`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '版本号' ,
`update_date`  datetime NOT NULL COMMENT '更新时间' ,
PRIMARY KEY (`oid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Dynamic
;

INSERT INTO `m1_common_version` (`version`, `update_date`) 
VALUES ('v1.0.1', now());
