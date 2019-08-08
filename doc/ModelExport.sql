CREATE TABLE `movie` (
`id` int(11) NOT NULL,
`directors` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`scriptwriter` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`actors` varchar(1500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`lang` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`publish_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`duration` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`imdb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`score` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`votes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`summary` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`year` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`comment1` longtext CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
`comment2` longtext CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
`comment3` longtext CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
`douban_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`single_duration` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`episodes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`) ,
UNIQUE INDEX `doubanid` (`douban_id`)
);

CREATE TABLE `movie_list` (
`id` int(11) NOT NULL,
`directors` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`rate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`cover_x` int(11) NOT NULL,
`star` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`casts` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`douban_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`cover_y` int(11) NOT NULL,
PRIMARY KEY (`id`) 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci;

