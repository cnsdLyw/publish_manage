package com.litc.common.util.fts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litc.common.util.Constant;
import com.litc.common.util.thread.WorkObjOperate;
import com.litc.common.util.thread.WorkTask;

/**
 * 业务逻辑处理类
 * 
 * @author sungao
 * @since 2015-12-28
 */
public class SolrIndexOperTask implements WorkTask {

	private static final long serialVersionUID = 417805089265851324L;

	private final static Logger logger = LoggerFactory
			.getLogger(SolrIndexOperTask.class);

	private String operType;

	private List<String> resIds;

	private String taskFlag;

	private String type;

	private String desc;

	public SolrIndexOperTask(String operType, List<String> resIds) {
		this.operType = operType;
		this.resIds = resIds;
		this.taskFlag = java.util.UUID.randomUUID().toString();
		this.type = Constant.TYPE_INDEX;
		this.desc = Constant.DESC_INDEX;
	}

	@Override
	public String getTaskFlag() {
		return this.taskFlag;
	}

	@Override
	public String getTaskType() {
		return this.type;
	}
	
	@Override
	public String getTaskDesc() {
		return this.desc;
	}

	@Override
	public void runTask() {
		/**
		List<ResFileInfo> resFileInfoList = null;
		ResBasicInfo resBasicInfo = null;
		if (operType.equals(Constant.METADATA)
				|| operType.equals(Constant.FILEDATA)) {
			Map<String, String> attrMap = null;
			for (String id : resIds) {
				resBasicInfo = SpringContextHandler.getBean(
						ResBasicInfoService.class).findOne(id);
				if (resBasicInfo == null) {
					continue;
				}
				attrMap = new ConcurrentHashMap<String, String>();
				String detailViewTemplate = SpringContextHandler.getBean(
						ResItemService.class).getResPropertyValue("",
						resBasicInfo.getRes_lib_id(),
						ITEM_PROP_NAME.View_DetailTemplate);
				String libid = resBasicInfo.getRes_lib_id();
				String libName = SpringContextHandler
						.getBean(ResItemService.class)
						.findOne(resBasicInfo.getRes_lib_id()).getItem_name();
				switch (operType) {
				case Constant.METADATA:
					attrMap.put("id", id);
					attrMap.put("title", resBasicInfo.getTitle());
					attrMap.put("type", operType);
					attrMap.put("createtime", resBasicInfo.getCreate_time());
					attrMap.put("updatetime", resBasicInfo.getUpdate_time());
					attrMap.put("creator", resBasicInfo.getCreator());
					attrMap.put("keyword", "");
					attrMap.put("pictureurl", "");
					attrMap.put("template", detailViewTemplate);
					attrMap.put("libid", libid);
					attrMap.put("libname", libName);
					Map<String, String> res = SpringContextHandler.getBean(
							ResStockInService.class).getResItemValue("", libid,
							id);
					for (Map.Entry<String, String> map : res.entrySet()) {
						if (map.getValue() != null && map.getKey() != null)
							attrMap.put(map.getKey() + Constant.INDEXEXTRE,
									map.getValue());
					}
					SolrDataImportHandler.indexMetaDataByTempFile(attrMap);
					cancelTask();
					break;
				case Constant.FILEDATA:
					resFileInfoList = SpringContextHandler.getBean(
							ResDownloadService.class).FindResFileList(id);
					for (ResFileInfo resFileInfo : resFileInfoList) {
						attrMap = new ConcurrentHashMap<String, String>();
						attrMap.put(
								"id",
								id + Constant.UNDERLINE
										+ resFileInfo.getFile_id());
						attrMap.put("title",
								resBasicInfo.getTitle() + Constant.LEFTBRACKETS
										+ resFileInfo.getFile_name()
										+ Constant.RIGHTBRACKETS);
						attrMap.put("type", operType);
						attrMap.put("template", detailViewTemplate);
						attrMap.put("libid", libid);
						attrMap.put("libname", libName);
						attrMap.put("updatetime", resFileInfo.getUpdate_time());
						try {
							SolrDataImportHandler.indexFilesSolrCell(attrMap,
									resFileInfoList);
						} catch (Exception e) {
							logger.error("添加文件索引异常", e.getMessage());
							e.printStackTrace();
						}
					}
					cancelTask();
					break;
				default: {
					WorkObjOperate.updateStatus(WorkObjOperate.EXCEPTION,
							this.taskFlag);
					throw new RuntimeException(
							"invalid index operate! operateCode=" + operType);

				}
				}
			}
		} else {
			SolrDataImportHandler.indexDelete(resIds);
			List<String> ids = new ArrayList<String>();
			for (String id : resIds) {
				resBasicInfo = SpringContextHandler.getBean(
						ResBasicInfoService.class).findOne(id);
				if (resBasicInfo == null) {
					continue;
				}
				resFileInfoList = SpringContextHandler.getBean(
						ResDownloadService.class).FindResFileList(id);
				for (ResFileInfo resFileInfo : resFileInfoList) {
					ids.add(resBasicInfo.getRes_id() + Constant.UNDERLINE
							+ resFileInfo.getFile_id());
				}
			}
			SolrDataImportHandler.indexDelete(ids);
			cancelTask();
		}
	*/
	}
	@Override
	public void cancelTask() {
		WorkObjOperate.updateStatus(WorkObjOperate.FINISH, this.taskFlag);
	}

	@Override
	public int getProgress() {
		return 0;
	}

}
