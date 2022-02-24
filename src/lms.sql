

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `lms`
--

-- --------------------------------------------------------

--
-- 表的结构 `books`
--

CREATE TABLE `books` (
  `bno` varchar(20) NOT NULL,
  `bname` varchar(20) NOT NULL,
  `author` varchar(20) NOT NULL,
  `publisher` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `publish_time` char(10) NOT NULL,
  `sum_amount` int NOT NULL,
  `left_amount` int NOT NULL,
  `type` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- 转存表中的数据 `books`
--

INSERT INTO `books` (`bno`, `bname`, `author`, `publisher`, `publish_time`, `sum_amount`, `left_amount`, `type`) VALUES
('A-201311-047803', '我的人生哲学', '张燕', '北京联合出版社', '2013年11月', 300, 300, '哲学'),
('A-202010-311182', '中国哲学史', '缪祥忠', '中国传媒大学出版社', '2020年10月', 200, 200, '哲学'),
('A-202107-731516', '逻辑哲学论', '维特根斯坦', '中国华侨出版社', '2021年07月', 200, 200, '哲学'),
('B-201705-571195', '圣经纵览', '约翰·里奇斯', '外语教学与研究出版社', '2017年05月', 100, 99, '宗教'),
('H-201709-512169', '骆驼祥子', '老舍', '人民教育出版社', '2017年09月', 500, 499, '文学'),
('H-201802-922971', '海底两万里', '儒勒·凡尔纳', '人民教育出版社', '2018年02月', 500, 497, '文学'),
('H-202106-493345', '朝花夕拾', '鲁迅', '北京师范大学出版社', '2021年06月', 300, 300, '文学'),
('H-202106-703361', '西游记', '吴承恩', '人民教育出版社', '2021年06月', 400, 400, '文学'),
('K-201505-243654', '经济学原理', '阿尔弗雷德·马歇尔', '北京联合出版公司', '2015年05月', 600, 600, '经济'),
('K-201709-159709', '资本论', '谢洪波', '中国华侨出版社', '2017年09月', 500, 500, '经济'),
('L-201805-246535', '战争论', '卡尔·冯·克劳赛维芡', '台海出版出版公司', '2018年05月', 300, 300, '军事'),
('M-202111-500629', '法治的细节', '罗翔', '云南人民出版社', '2021年11月', 600, 600, '法律'),
('N-202106-669736', '计算机操作系统', '汤小丹', '人民邮电出版社', '2021年06月', 100, 100, '教育'),
('N-202106-915117', '计算机网络', '谢希仁', '电子工业出版社', '2021年06月', 150, 150, '教育'),
('N-202108-758377', '数据库原理及应用', '岳静', '科学出版社', '2021年08月', 200, 199, '教育'),
('U-200910-412333', '中国考古通论', '张之恒', '南京大学出版社', '2009年10月', 400, 400, '考古'),
('U-201812-785976', '田野考古学', '冯恩学', '吉林大学出版社', '2018年12月', 400, 400, '考古');

-- --------------------------------------------------------

--
-- 表的结构 `borrow`
--

CREATE TABLE `borrow` (
  `account_no` char(6) NOT NULL,
  `bno` varchar(20) NOT NULL,
  `borrow_time` varchar(30) NOT NULL,
  `renew` char(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- 转存表中的数据 `borrow`
--

INSERT INTO `borrow` (`account_no`, `bno`, `borrow_time`, `renew`) VALUES
('123456', 'H-201709-512169', '2021-12-20', '是'),
('123456', 'H-201802-922971', '2021-12-25', '否'),
('727054', 'H-201802-922971', '2022-01-25', '否'),
('862774', 'B-201705-571195', '2021-12-19', '否'),
('862774', 'N-202108-758377', '2021-12-19', '否'),
('868181', 'H-201802-922971', '2021-12-23', '否');

-- --------------------------------------------------------

--
-- 表的结构 `reader`
--

CREATE TABLE `reader` (
  `account_no` char(6) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `lend_amount` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- 转存表中的数据 `reader`
--

INSERT INTO `reader` (`account_no`, `name`, `password`, `lend_amount`) VALUES
('123456', 'test', '123456', 0),
('727054', '123456', '123456', 0),
('862774', 'wangyu', '070701wy', 0),
('868181', 'xyq', '123123', 0);

--
-- 转储表的索引
--

--
-- 表的索引 `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`bno`);

--
-- 表的索引 `borrow`
--
ALTER TABLE `borrow`
  ADD PRIMARY KEY (`account_no`,`bno`),
  ADD KEY `FK_bno` (`bno`);

--
-- 表的索引 `reader`
--
ALTER TABLE `reader`
  ADD PRIMARY KEY (`account_no`);

--
-- 限制导出的表
--

--
-- 限制表 `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `FK_bno` FOREIGN KEY (`bno`) REFERENCES `books` (`bno`),
  ADD CONSTRAINT `FK_sno` FOREIGN KEY (`account_no`) REFERENCES `reader` (`account_no`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
