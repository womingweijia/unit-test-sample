create database unittest;
use unittest;
drop table if exists `user`;
create table `user`(
    `id` int(10) unsigned not null COMMENT 'id' auto_increment,
    `name` varchar(20) not null COMMENT '姓名',
    `sex` boolean COMMENT '性别',
    `section` varchar(20) COMMENT '部门',
    `birthday` DATE COMMENT '出生日期',
    `fromDate` DATE COMMENT '进入公司日期',
    `updateTime` DATETIME COMMENT '更新时间',
    `status` tinyint(2) COMMENT '状态' DEFAULT 0,
    `img` varchar(100) COMMENT '头像',
    `resume` varchar(100) COMMENT '简历',
     primary key (`id`)
)engine=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `user` (`name`, sex, `section`, birthday, `fromDate`, updateTime) VALUES ('李白杨', 1, '研发', DATE_FORMAT ( '1990-12-20' , '%Y-%m-%d' ), DATE_FORMAT ( '2017-1-10' , '%Y-%m-%d' ), NOW());|
INSERT INTO `user` (`name`, sex, `section`, birthday, `fromDate`, updateTime) VALUES ('卢红梅', 0, '研发', DATE_FORMAT ( '1992-10-20' , '%Y-%m-%d' ), DATE_FORMAT ( '2017-1-10' , '%Y-%m-%d' ), NOW());|
INSERT INTO `user` (`name`, sex, `section`, birthday, `fromDate`, updateTime) VALUES ('江峰', 1, '研发', DATE_FORMAT ( '1993-12-28' , '%Y-%m-%d' ), DATE_FORMAT ( '2017-2-11' , '%Y-%m-%d' ), NOW());|
INSERT INTO `user` (`name`, sex, `section`, birthday, `fromDate`, updateTime) VALUES ('胡立志', 1, '财务', DATE_FORMAT ( '1990-01-20' , '%Y-%m-%d' ), DATE_FORMAT ( '2017-1-17' , '%Y-%m-%d' ), NOW());|
INSERT INTO `user` (`name`, sex, `section`, birthday, `fromDate`, updateTime) VALUES ('孔芳', 0, '行政', DATE_FORMAT ( '1992-09-14' , '%Y-%m-%d' ), DATE_FORMAT ( '2017-3-10' , '%Y-%m-%d' ), NOW());|
