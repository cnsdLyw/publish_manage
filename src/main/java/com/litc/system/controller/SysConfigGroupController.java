package com.litc.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.litc.security.controller.BaseController;
import com.litc.system.model.SysConfigGroup;
import com.litc.system.service.SysConfigGroupService;

@Controller
@RequestMapping("/sysConfigGroup")
public class SysConfigGroupController extends BaseController<SysConfigGroup> {

	@Autowired
	private SysConfigGroupService sysConfigGroupService;
	
	private static final int AUTHORITYPAGESIZE = 12;
	private static String ORDER_TYPE = "id";
	
	@RequestMapping(value = "/list")
	public ModelAndView listFolder(HttpServletRequest request,
			String keyWord,Long typeid) throws Exception {
		ModelAndView modelAndView = new ModelAndView("system/configure/configGroup-list");
		String parentName=null;
		//配置文件测试
		pageNo = this.getInt(request, "pageNo");
		/**模拟数据 start*/
		if(typeid!=0l){
			SysConfigGroup sysConfigGroup =sysConfigGroupService.getSysConfigGroupBy(typeid);
			parentName =sysConfigGroup.getGroupName();
		}
		page = sysConfigGroupService.getSysConfigGroupsByPage(pageNo, AUTHORITYPAGESIZE, SORT_TYPE_ASC,ORDER_TYPE, typeid,keyWord);
		if(typeid==0l){
			page = sysConfigGroupService.getSysConfigGroupsByPage(pageNo, AUTHORITYPAGESIZE, SORT_TYPE_ASC,ORDER_TYPE, typeid,keyWord);
			parentName = "无";
		}
		modelAndView.addObject("pageContent",page);
		modelAndView.addObject("typeid",typeid);
		modelAndView.addObject("keyWord",keyWord);
		modelAndView.addObject("parentName", parentName);
		/**模拟数据 end*/
		return modelAndView;
	}
	
	//添加新的配置信息
			@RequestMapping(value = "/addSysConfig", method = RequestMethod.GET)
			public ModelAndView addSysConfig(HttpServletRequest request,Long typeid) {
				ModelAndView modelAndView = new ModelAndView("system/configure/configGroup-add");
				modelAndView.addObject("SysConfigGroup", new SysConfigGroup());
				modelAndView.addObject("typeid",typeid);
				return modelAndView;
			}
			
			//编辑配置信息
			@RequestMapping(value="/editSysConfig/{id}", method=RequestMethod.GET)
			public ModelAndView editSysConfig(@PathVariable Long id,Long typeid) {
				ModelAndView modelAndView = new ModelAndView("system/configure/configGroup-add");
				SysConfigGroup sysConfigGroup = sysConfigGroupService.getSysConfigGroupBy(id);
				modelAndView.addObject("sysConfigGroup",sysConfigGroup);
				modelAndView.addObject("typeid",typeid);
				return modelAndView;
				
			}
			
			//保存配置
			@RequestMapping(value = "/save", method = RequestMethod.POST)
			public ModelAndView saveSysConfigure(SysConfigGroup sysConfigGroup,HttpServletRequest request,Long typeid) {
				ModelAndView modelAndView = new ModelAndView("redirect:/sysConfirure/list/");
				sysConfigGroup.setParentId(typeid);
				sysConfigGroupService.addSysConfigGroup(sysConfigGroup);
				modelAndView.addObject("typeid",typeid);
				return modelAndView;
			}
			
			@RequestMapping(value="/delete/{ids}", method=RequestMethod.GET)
			public ModelAndView delete(@PathVariable String ids,HttpServletRequest request,Long typeid) {
				ModelAndView modelAndView = new ModelAndView("redirect:/sysConfigGroup/list/");
				Long[] Id=getIdArray(ids);
				for(Long id:Id){
					sysConfigGroupService.delete(id);
				}
				modelAndView.addObject("typeid",typeid);
				return modelAndView;
			}
			
			@RequestMapping(value="/isSysConfigGroupNameExist", method=RequestMethod.GET)
			@ResponseBody
			public boolean isSysConfigGroupNameExist(String groupName){
				return sysConfigGroupService.isSysConfigureNameExist(groupName);
			}
			
			@RequestMapping(value="/isSysConfigGroupNameExistWithId", method=RequestMethod.GET)
			@ResponseBody
			public boolean isSysConfigGroupNameExistWithId(Long id,String groupName){
				return sysConfigGroupService.isSysConfigureNameExist(id,groupName);
			}
	
			//fuelux获取树的信息
			@RequestMapping(value="/getTreeData", method=RequestMethod.GET)
			@ResponseBody
			public Object getTreeData(Long typeid){
				String tree_data ="{";
				String type="";
				List<SysConfigGroup> sysConfigGroupList = sysConfigGroupService.getSysConfigGroup(0l);
				for(int i=0;i<sysConfigGroupList.size();i++){
					List<SysConfigGroup> sysConfigGroupList1 = sysConfigGroupService.getSysConfigGroup(sysConfigGroupList.get(i).getId());
					if(sysConfigGroupList.size()-1-i>0){
						int count = sysConfigGroupService.getCounts(sysConfigGroupList.get(i).getId());
						if(count>0){
							type = "folder";
						}else{
							type = "item";
						}
						tree_data += "'"+ sysConfigGroupList.get(i).getGroupName()+"':{text:'"+sysConfigGroupList.get(i).getGroupName()+"',type:'"+type+"','id':'"+sysConfigGroupList.get(i).getId()+"',";
						if(sysConfigGroupList.get(i).getId()==typeid){
							tree_data+="'opened':'true',";
						}
						if(sysConfigGroupList1.size()>0){
							tree_data+="'additionalParameters':{'children':{";
							for(int j=0;j<sysConfigGroupList1.size();j++){
								count = sysConfigGroupService.getCounts(sysConfigGroupList1.get(j).getId());
								if(count>0){
									type = "folder";
								}else{
									type = "item";
								}
								
								if(j<sysConfigGroupList1.size()-1){
									tree_data+="'"+sysConfigGroupList1.get(j).getGroupName()+"':{text:'"+sysConfigGroupList1.get(j).getGroupName()+"',type:'"+type+"','id':'"+sysConfigGroupList1.get(j).getId()+"'},";
								}else{
									count = sysConfigGroupService.getCounts(sysConfigGroupList1.get(j).getId());
									if(count>0){
										type = "folder";
									}else{
										type = "item";
									}
									tree_data+="'"+sysConfigGroupList1.get(j).getGroupName()+"':{text:'"+sysConfigGroupList1.get(j).getGroupName()+"',type:'"+type+"','id':'"+sysConfigGroupList1.get(j).getId()+"'}}}},";
								}
							}
						}else{
							tree_data+="},";
						}
					}else{
						int count = sysConfigGroupService.getCounts(sysConfigGroupList.get(i).getId());
						if(count>0){
							type = "folder";
						}else{
							type = "item";
						}
						tree_data += "'"+ sysConfigGroupList.get(i).getGroupName()+"':{text:'"+sysConfigGroupList.get(i).getGroupName()+"',type:'"+type+"','id':'"+sysConfigGroupList.get(i).getId()+"'";
						if(sysConfigGroupList.get(i).getId()==typeid){
							tree_data+=",'opened':'true',";
						}
						if(sysConfigGroupList1.size()>0){
							tree_data+=",'additionalParameters':{'children':{";
							for(int j=0;j<sysConfigGroupList1.size();j++){
								if(j<sysConfigGroupList1.size()-1){
									count = sysConfigGroupService.getCounts(sysConfigGroupList1.get(j).getId());
									if(count>0){
										type = "folder";
									}else{
										type = "item";
									}	
									
									tree_data+="'"+sysConfigGroupList1.get(j).getGroupName()+"':{text:'"+sysConfigGroupList1.get(j).getGroupName()+"',type:'"+type+"','id':'"+sysConfigGroupList1.get(j).getId()+"'},";
								}else{
									count = sysConfigGroupService.getCounts(sysConfigGroupList1.get(j).getId());
									if(count>0){
										type = "folder";
									}else{
										type = "item";
									}	
									tree_data+="'"+sysConfigGroupList1.get(j).getGroupName()+"':{text:'"+sysConfigGroupList1.get(j).getGroupName()+"',type:'"+type+"','id':'"+sysConfigGroupList1.get(j).getId()+"'}}}}}";
								}
							}
						}else{
							tree_data += "}}";
						}
					}
				}
				Object data = JSON.parse(tree_data);
				return data;
			}
			
//			public String tree_data(String tree_data,List<SysConfigGroup> list,String type,int count){
//				int counts = 0 ;
//					tree_data+=",'additionalParameters':{'children':{";
//					for(int j=0;j<list.size();j++){
//						List<SysConfigGroup> list1 = sysConfigGroupService.getSysConfigGroup(list.get(j).getId());
//						count = sysConfigGroupService.getCounts(list.get(j).getId());
//						if(count>0){
//							type = "folder";
//						}else{
//							type = "item";
//						}
//						
//						if(j<list.size()-1){
//						tree_data+="'"+list.get(j).getGroupName()+"':{text:'"+list.get(j).getGroupName()+"',type:'"+type+"','id':'"+list.get(j).getId()+"'";
//						if(list1.size()>0){
//							tree_data = tree_data(tree_data,list1,type,count);
//							++counts;
//						}
//						}else{
//							count = sysConfigGroupService.getCounts(list.get(j).getId());
//							if(count>0){
//								type = "folder";
//							}else{
//								type = "item";
//							}
//							tree_data+="'"+list.get(j).getGroupName()+"':{text:'"+list.get(j).getGroupName()+"',type:'"+type+"','id':'"+list.get(j).getId()+"'";
//							if(list1.size()>0){
//								tree_data = tree_data(tree_data,list1,type,count);
//							}else{
//								for(int i=0;i<counts-1;i++){
//									tree_data+="}";
//								}
//								tree_data += "}}}},";
//							}
//						}
//						}
//				
//				return tree_data;
//			}
//			
//	//fuelux获取树的信息
//			@RequestMapping(value="/getTreeData", method=RequestMethod.GET)
//			@ResponseBody
//			public Object getTreeData(Long typeid){
//				String tree_data ="{";
//				String type="";
//				List<SysConfigGroup> sysConfigGroupList = sysConfigGroupService.getSysConfigGroup(0l);
//				for(int i=0;i<sysConfigGroupList.size();i++){
//					List<SysConfigGroup> sysConfigGroupList1 = sysConfigGroupService.getSysConfigGroup(sysConfigGroupList.get(i).getId());
//						if(sysConfigGroupList.size()-1-i>0){
//							int count = sysConfigGroupService.getCounts(sysConfigGroupList.get(i).getId());
//							if(count>0){
//								type = "folder";
//							}else{
//								type = "item";
//							}
//							tree_data += "'"+ sysConfigGroupList.get(i).getGroupName()+"':{text:'"+sysConfigGroupList.get(i).getGroupName()+"',type:'"+type+"','id':'"+sysConfigGroupList.get(i).getId()+"'";
//							if(sysConfigGroupList.get(i).getId()==typeid){
//								tree_data+=",'opened':'true',";
//							}
//							if(sysConfigGroupList1.size()>0){
//								tree_data = tree_data(tree_data,sysConfigGroupList1,type,count);
//							}else{
//								tree_data+="}";
//							}
//						}else{
//							int count = sysConfigGroupService.getCounts(sysConfigGroupList.get(i).getId());
//							if(count>0){
//								type = "folder";
//							}else{
//								type = "item";
//							}
//							tree_data += "'"+ sysConfigGroupList.get(i).getGroupName()+"':{text:'"+sysConfigGroupList.get(i).getGroupName()+"',type:'"+type+"','id':'"+sysConfigGroupList.get(i).getId()+"'";
//							if(sysConfigGroupList.get(i).getId()==typeid){
//								tree_data+=",'opened':'true',";
//							}
//							if(sysConfigGroupList1.size()>0){
//								tree_data+=",'additionalParameters':{'children':{";
//								for(int j=0;j<sysConfigGroupList1.size();j++){
//									if(j<sysConfigGroupList1.size()-1){
//										count = sysConfigGroupService.getCounts(sysConfigGroupList1.get(j).getId());
//										if(count>0){
//											type = "folder";
//										}else{
//											type = "item";
//										}	
//									
//									tree_data+="'"+sysConfigGroupList1.get(j).getGroupName()+"':{text:'"+sysConfigGroupList1.get(j).getGroupName()+"',type:'"+type+"','id':'"+sysConfigGroupList1.get(j).getId()+"'}";
//									}else{
//										count = sysConfigGroupService.getCounts(sysConfigGroupList1.get(j).getId());
//										if(count>0){
//											type = "folder";
//										}else{
//											type = "item";
//										}	
//										tree_data+="'"+sysConfigGroupList1.get(j).getGroupName()+"':{text:'"+sysConfigGroupList1.get(j).getGroupName()+"',type:'"+type+"','id':'"+sysConfigGroupList1.get(j).getId()+"'}}}}}}}}";
//									}
//									}
//							}else{
//								tree_data += "}}}}";
//							}
//						}
//				}
//				Object data = JSON.parse(tree_data);
//				return data;
//			}
			
//			//ztree获取树的信息
//			@RequestMapping(value="/getTreeData", method=RequestMethod.GET)
//			public String getTreeData(Long typeid, Model model){
//				List<SysConfigGroup> sysConfigGroupList1 = sysConfigGroupService.getSysConfigGroup();
//				JSONArray jsonArray = new JSONArray();
//				for(SysConfigGroup obj:sysConfigGroupList1){
//					JSONObject js = new JSONObject();
//					js.put("id", obj.getId());
//					js.put("name", obj.getGroupName());
//					js.put("pId", obj.getParentId());
//					jsonArray.put(js);
//				}
//				System.out.println(jsonArray);
//				model.addAttribute("jsonArray", jsonArray);
//				return "system/configure/NewFile";
//			}
//			
//			@RequestMapping("/delete")
//			@ResponseBody
//			public String deleteResItem(@RequestParam(value="id",required=true) Long id){
//				sysConfigGroupService.delete(id);
//				return "1";
//			}
			
	
}
