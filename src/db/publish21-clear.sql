-- 清理表
delete from jc_file_obj;
delete from jc_file;
delete from jc_filecenter;
delete from jc_book_message;
delete from jc_message;
delete from jc_borrow;
delete from jc_issue;
delete from jc_ceb_limit;
delete from jc_book;
delete from process_task_flow;
delete from process_task;
delete from compliancetest_task_flow;
delete from compliancetest_task;


 
 
-- 重新建表
/*
DROP TABLE IF EXISTS `jc_book_message`;
DROP TABLE IF EXISTS `jc_ceb_limit`;
DROP TABLE IF EXISTS `jc_issue`;
DROP TABLE IF EXISTS `jc_borrow`;
DROP TABLE IF EXISTS `jc_book`;

DROP TABLE IF EXISTS `jc_file_obj`;
DROP TABLE IF EXISTS `jc_file`;
DROP TABLE IF EXISTS `jc_file_obj`;
DROP TABLE IF EXISTS `jc_filecenter`; 
*/
-- DROP TABLE IF EXISTS `cms_manuscript`;
-- DROP TABLE IF EXISTS `cms_faq`;

--ALTER TABLE jc_book ADD  `displayorder` double(10,2)  default '0.00' COMMENT '排序码';
--ALTER TABLE jc_book modify   `displayorder` double(12,2)  default '0.00' COMMENT '排序码';
