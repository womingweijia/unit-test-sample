create database unittest;         //库名
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
