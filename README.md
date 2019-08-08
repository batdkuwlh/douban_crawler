# douban_crawler
豆瓣爬虫  
请求路径：http://localhost:8088/g?id=26884354  
参数id填写豆瓣id  
第一次请求后会将从豆瓣来的数据写入数据库，后续请求将不再请求豆瓣，直接从数据库获取  
返回数据为JSON格式，如下： 
```json
{  "summary": "　　非洲大草原上一轮红日冉冉升起，为高大的乞力马扎罗山披上层金色的光纱，所有的动物涌向了同一个地方——荣耀石，兴奋地等待着一个重大消息的宣布：它们的国王木法沙将迎来自己的新生儿。 　　这个新生儿就是小狮子辛巴，它是木法沙的法定接班人、荣耀石未来的国王。 　　小狮子王辛巴在众多热情的朋友的陪伴下，不但经历了生命中最光荣的时刻，也遭遇了最艰难的挑战，最后终于成为了森林之王，也在周而复始生生不息的自然中体会出生命的真义。",
	"comment3": "%E6%88%90%E4%B9%9F%E6%8A%80%E6%9C%AF%EF%BC%8C%E8%B4%A5%E4%B9%9F%E6%8A%80%E6%9C%AF%E3%80%82%E7%AE%80%E7%9B%B4%E8%B7%9F%E7%9C%9F%E5%8A%A8%E7%89%A9%E6%BC%94%E7%9A%84%E6%B2%A1%E5%95%A5%E5%8C%BA%E5%88%AB%EF%BC%8C%E6%AF%9B%E5%8F%91%E3%80%81%E8%82%8C%E8%82%89%E5%81%9A%E7%9A%84%E9%83%BD%E6%97%A0%E5%8F%AF%E6%8C%91%E5%89%94%E3%80%82%E4%BD%86%E5%9B%A0%E6%AD%A4%E7%BC%BA%E5%B0%91%E4%BA%86%E5%85%A5%E6%88%8F%E7%9A%84%E6%B0%9B%E5%9B%B4%EF%BC%8C%E7%9C%9F%E5%8A%A8%E7%89%A9%E6%80%8E%E4%B9%88%E5%8F%AF%E8%83%BD%E7%8B%AE%E5%AD%90%E5%92%8C%E9%AC%A3%E7%8B%97%E7%8B%BC%E7%8B%88%E4%B8%BA%E5%A5%B8%E5%95%8A%EF%BC%81%E6%83%B3%E9%87%8D%E6%B8%A9%E7%AB%A5%E5%B9%B4%E7%BB%8F%E5%85%B8%E7%9A%84%E5%8F%AF%E4%BB%A5%E5%8E%BB%E7%9C%8B%E7%9C%8B%EF%BC%8C%E5%BF%A0%E5%AE%9E%E8%BF%98%E5%8E%9F%EF%BC%8C%E6%B2%A1%E6%9C%89%E7%AA%81%E7%A0%B4%E3%80%82",
	"comment2": "%E6%8A%80%E6%9C%AF%E7%A1%AE%E5%AE%9E%E7%89%9B%E9%80%BC%EF%BC%8C%E5%87%A0%E5%8F%AF%E4%B9%B1%E7%9C%9F%E3%80%82%E4%BD%86%E6%98%AF%EF%BC%8C%EF%BC%8C%EF%BC%8C%E7%9C%9F%E7%9A%84%E6%9C%89%E5%BF%85%E8%A6%81%E4%B9%B1%E7%9C%9F%E4%B9%88%EF%BC%8C%E5%BD%93%E9%87%8C%E9%9D%A2%E7%9A%84%E5%8A%A8%E7%89%A9%E5%AE%8C%E5%85%A8%E5%8F%98%E5%BE%97%E5%92%8C%E7%9C%9F%E7%9A%84%E4%B8%80%E6%A0%B7%EF%BC%8C%E6%81%90%E6%80%95%E4%B9%9F%E5%BE%88%E9%9A%BE%E7%94%A8%E5%BD%93%E5%B9%B4%E5%8A%A8%E7%94%BB%E7%9A%84%E8%A7%84%E5%88%99%E6%9D%A5%E6%8E%A5%E5%8F%97%E8%BF%99%E9%83%A8%E7%94%B5%E5%BD%B1%E4%BA%86%E3%80%82%E5%8A%A8%E7%94%BB%E5%8F%AF%E4%BB%A5%E6%98%AF%E7%AB%A5%E8%AF%9D%EF%BC%8C%E5%8F%AF%E4%BB%A5%E9%80%A0%E6%A2%A6%EF%BC%8C%E4%BD%86%E7%8E%B0%E5%AE%9E%E9%87%8C%EF%BC%8C%E7%8B%AE%E5%AD%90%E5%92%8C%E9%B8%9F%E3%80%81%E7%8C%AA%E6%98%AF%E6%9C%8B%E5%8F%8B%EF%BC%9F%E7%8B%AE%E5%AD%90%E9%9D%A0%E5%90%83%E8%99%AB%E5%AD%90%E6%B4%BB%E4%B8%8B%E6%9D%A5%EF%BC%9F%EF%BC%9F%EF%BC%9F%E4%B8%80%E5%88%87%E9%83%BD%E5%8F%98%E5%BE%97%E6%80%AA%E6%80%AA%E7%9A%84%E4%BA%86%E3%80%82%E5%BD%93%E7%84%B6%EF%BC%8C%E9%97%AE%E9%A2%98%E8%BF%9C%E4%B8%8D%E4%BB%85%E5%A6%82%E6%AD%A4%E3%80%82%E5%B0%B1%E5%A5%BD%E5%83%8F%EF%BC%8C%E4%BD%A0%E6%97%A0%E6%B3%95%E7%9C%9F%E6%AD%A3%E4%B8%8E%E7%89%87%E4%B8%AD%E4%BB%BB%E4%BD%95%E4%B8%80%E4%B8%AA%E8%A7%92%E8%89%B2%E5%BB%BA%E7%AB%8B%E6%83%85%E6%84%9F%EF%BC%8C%E5%9B%A0%E4%B8%BA%EF%BC%8C%E6%95%B4%E4%BD%93%E8%A7%92%E8%89%B2%E8%AE%BE%E7%BD%AE%E9%83%BD%E5%A4%AA%E6%B5%AE%E7%9A%AE%E6%BD%A6%E8%8D%89%E4%BA%86%E3%80%82%E4%BB%A5%E5%8F%8A%E3%80%8220%E5%B9%B4%E5%A4%9A%E5%89%8D%E8%A7%89%E5%BE%97%E7%BB%8F%E5%85%B8%E7%9A%84%E6%95%85%E4%BA%8B%EF%BC%8C%E7%AE%80%E5%8D%95%E5%A4%8D%E5%88%BB%E4%B9%8B%E5%90%8E%EF%BC%8C%E8%83%BD%E5%90%A6%E6%BB%A1%E8%B6%B3%E7%8E%B0%E5%9C%A8%E7%9A%84%E8%A7%82%E4%BC%97",
	"year": "2019",
	"comment1": "%E5%BD%B1%E5%8E%85%E9%87%8C%E5%85%A8%E6%98%AF30%E5%A4%9A%E5%B2%81%E7%9A%84%E5%B0%8F%E5%B1%81%E5%AD%A9...",
	"single_duration": "",
	"directors": "乔恩·费儒",
	"douban_id": "26884354",
	"type": "剧情,动画,冒险",
	"title": "狮子王",
	"duration": "118分钟",
	"cover": "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2559742751.webp",
	"actors": "唐纳德·格洛弗 , 阿尔法·伍达德 , 詹姆斯·厄尔·琼斯 , 切瓦特·埃加福特 , 科甘-迈克尔·凯 , 塞斯·罗根 , 比利·艾希纳 , 碧昂丝·诺尔斯 , 埃里克·安德烈 , 弗洛伦丝·卡松巴 , 约翰·卡尼 , 约翰·奥利弗 , 艾米·塞德丽丝",
	"score": "7.4",
	"imdb": "",
	"alias": "狮子王真狮版 , 狮子王真实版 , 狮子王真人版",
	"votes": "177129",
	"id": 1,
	"region": "美国",
	"lang": "英语 , 科萨语",
	"scriptwriter": "杰夫·内桑森 , 布兰达∙查普曼 , 艾琳·梅琪 , 乔纳森·罗伯茨 , 琳达·伍尔芙顿",
	"publish_date": "2019-07-12(中国大陆),2019-07-19(美国)",
	"episodes": ""
}
