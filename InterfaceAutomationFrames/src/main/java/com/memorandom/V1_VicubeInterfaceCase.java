//package com.cases;
//
//import com.config.VicubeCasesConfig;
//import com.utils.AssertFunction;
//import com.utils.HttpFunction;
//import com.utils.InterceptFunction;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.testng.annotations.Test;
//
//@SuppressWarnings("ALL")
//public class V1_VicubeInterfaceCase {
//    private static HttpFunction httpFunction = HttpFunction.getInstance();
//
//    @Test
//    public void queryUserInfo(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryUserInfoURL);
//        String result=String.valueOf(post);
//        String name = InterceptFunction.intercept(result, "\"name\":\"wgf\"");
//        String authMethod = InterceptFunction.intercept(result, "\"authMethod\":\"DB\"");
//        AssertFunction.assertEquals(name,"\"name\":\"wgf\"");
//        AssertFunction.assertEquals(authMethod,"\"authMethod\":\"DB\"");
//    }
//
//    @Test
//    public void DoLogin(){
//        Object post = httpFunction.post(VicubeCasesConfig.DoLoginURL,VicubeCasesConfig.DoLoginParams);
//        String result=String.valueOf(post);
//        String encryptedpwd = InterceptFunction.intercept(result, "\"encryptedpwd\":\"@Vic_2017_Rg\"");
//        String groupName = InterceptFunction.intercept(result, "\"groupName\":\"超级管理员组\"");
//        AssertFunction.assertEquals(encryptedpwd,"\"encryptedpwd\":\"@Vic_2017_Rg\"");
//        AssertFunction.assertEquals(groupName,"\"groupName\":\"超级管理员组\"");
//    }
//
//    @Test
//    public void getFunctionsByUserId(){
//        Object post = httpFunction.post(VicubeCasesConfig.getFunctionsByUserIdURL,VicubeCasesConfig.getFunctionsByUserIdParams);
//        String result=String.valueOf(post);
//        String name = InterceptFunction.intercept(result, "\"name\":\"班次内总任务操作数\"");
//        String code = InterceptFunction.intercept(result, "\"code\":\"F004007002\"");
//        AssertFunction.assertEquals(name,"\"name\":\"班次内总任务操作数\"");
//        AssertFunction.assertEquals(code,"\"code\":\"F004007002\"");
//    }
//
//    @Test
//    public void queryAllObjectsByUserId(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryAllObjectsByUserIdURL,VicubeCasesConfig.queryAllObjectsByUserIdParams);
//        String result=String.valueOf(post);
//        String objectId = InterceptFunction.intercept(result, "\"objectId\":\"8910\"");
//        String objectType = InterceptFunction.intercept(result, "\"objectType\":\"4\"");
//        AssertFunction.assertEquals(objectId,"\"objectId\":\"8910\"");
//        AssertFunction.assertEquals(objectType,"\"objectType\":\"4\"");
//    }
//
//    @Test
//    public void getUserViewsByType(){
//        Object post = httpFunction.post(VicubeCasesConfig.getUserViewsByTypeURL,VicubeCasesConfig.getUserViewsByTypeParams);
//        String result=String.valueOf(post);
//        String creator = InterceptFunction.intercept(result, "\"creator\":\"administrator\"");
//        String updator = InterceptFunction.intercept(result, "\"updator\":\"administrator\"");
//        AssertFunction.assertEquals(creator,"\"creator\":\"administrator\"");
//        AssertFunction.assertEquals(updator,"\"updator\":\"administrator\"");
//    }
//
//
//    //    返回为[]
//    @Test
//    public void getMyViewEntriesNoContent(){
//        Object post = httpFunction.post(VicubeCasesConfig.getMyViewEntriesNoContentURL,VicubeCasesConfig.getMyViewEntriesNoContentParams);
//        String result=String.valueOf(post);
//        String entryId = InterceptFunction.intercept(result, "\"entryId\":8910");
//        String creator = InterceptFunction.intercept(result, "\"creator\":\"administrator\"");
//        AssertFunction.assertEquals(entryId,"\"entryId\":8910");
//        AssertFunction.assertEquals(creator,"\"creator\":\"administrator\"");
//    }
//    @Test
//    public void queryGroupInfoLike(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryGroupInfoLikeURL,VicubeCasesConfig.queryGroupInfoLikeParams);
//        String result=String.valueOf(post);
//        String groupId = InterceptFunction.intercept(result, "\"groupId\":\"administrator\"");
//        String groupName = InterceptFunction.intercept(result, "\"groupName\":\"超级管理员组\"");
//        AssertFunction.assertEquals(groupId,"\"groupId\":\"administrator\"");
//        AssertFunction.assertEquals(groupName,"\"groupName\":\"超级管理员组\"");
//    }
//
//    @Test
//    public void getAllDomains(){
//        Object post = httpFunction.post(VicubeCasesConfig.getAllDomainsURL);
//        String result=String.valueOf(post);
//        String domain = InterceptFunction.intercept(result, "\"domain\":\"rootDomain\"");
//        String label = InterceptFunction.intercept(result, "\"label\":\"北京运维组\"");
//        AssertFunction.assertEquals(domain,"\"domain\":\"rootDomain\"");
//        AssertFunction.assertEquals(label,"\"label\":\"北京运维组\"");
//    }
//
//    //      接口不通过
//    @Test
//    public void createGroup(){
//        Object post = httpFunction.post(VicubeCasesConfig.createGroupURL,VicubeCasesConfig.createGroupParams);
//        String result=String.valueOf(post);
//        if("null".equals(InterceptFunction.intercept(result, "null"))) {
//            String aNull = InterceptFunction.intercept(result, "null");
//            AssertFunction.assertEquals(aNull, "null");
//        }else {
//            String flag = InterceptFunction.intercept(result, "\"flag\":1");
//            String groupName = InterceptFunction.intercept(result, "\"groupName\":\"收集接口使用123456\"");
//            AssertFunction.assertEquals(flag, "\"flag\":1");
//            AssertFunction.assertEquals(groupName, "\"groupName\":\"收集接口使用123456\"");
//        }
//    }
//
//    @Test
//    public void addView(){
//        Object post = httpFunction.post(VicubeCasesConfig.addViewURL,VicubeCasesConfig.addViewParams);
//        String result=String.valueOf(post);
//        String creator = InterceptFunction.intercept(result, "\"creator\":\"345174431410000\"");
//        String protectLevel = InterceptFunction.intercept(result, "\"protectLevel\":1");
//        AssertFunction.assertEquals(creator,"\"creator\":\"345174431410000\"");
//        AssertFunction.assertEquals(protectLevel,"\"protectLevel\":1");
//    }
//
//    @Test
//    public void grantObjects2Group(){
//        Object post = httpFunction.post(VicubeCasesConfig.grantObjects2GroupURL,VicubeCasesConfig.grantObjects2GroupParams);
//        String result=String.valueOf(post);
//        String number = InterceptFunction.intercept(result, "1");
//        AssertFunction.assertEquals(number,"1");
//    }
//
//    @Test
//    public void queryGroupInfo(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryGroupInfoURL);
//        String result=String.valueOf(post);
//        String creator = InterceptFunction.intercept(result, "\"groupName\":\"超级管理员组\"");
//        String protectLevel = InterceptFunction.intercept(result, "\"groupId\":\"administrator\"");
//        AssertFunction.assertEquals(creator,"\"groupName\":\"超级管理员组\"");
//        AssertFunction.assertEquals(protectLevel,"\"groupId\":\"administrator\"");
//    }
//
//    @Test
//    public void queryUsersByGroupId(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryUsersByGroupIdURL,VicubeCasesConfig.queryUsersByGroupIdParams);
//        String result=String.valueOf(post);
//        String creator = InterceptFunction.intercept(result, "[]");
//        AssertFunction.assertEquals(creator,"[]");
//    }
//
//    @Test
//    public void grantUsers2Group(){
//        Object post = httpFunction.post(VicubeCasesConfig.grantUsers2GroupURL,VicubeCasesConfig.grantUsers2GroupParams);
//        String result=String.valueOf(post);
//        String creator = InterceptFunction.intercept(result, "1");
//        AssertFunction.assertEquals(creator,"1");
//    }
//
//    @Test
//    public void disableGroup(){
//        Object post = httpFunction.post(VicubeCasesConfig.disableGroupURL,VicubeCasesConfig.disableGroupParams);
//        String result=String.valueOf(post);
//        String creator = InterceptFunction.intercept(result, "1");
//        AssertFunction.assertEquals(creator,"1");
//    }
//
//    @Test
//    public void getViewsExcludeKpiAndDeskTop(){
//        Object post = httpFunction.post(VicubeCasesConfig.getViewsExcludeKpiAndDeskTopURL);
//        String result=String.valueOf(post);
//        String creator = InterceptFunction.intercept(result, "\"creator\":\"administrator\"");
//        AssertFunction.assertEquals(creator,"\"creator\":\"administrator\"");
//        String protectLevel = InterceptFunction.intercept(result, "\"protectLevel\":1");
//        AssertFunction.assertEquals(protectLevel,"\"protectLevel\":1");
//    }
//
//    @Test
//    public void deleteGroup(){
//        Object post = httpFunction.post(VicubeCasesConfig.deleteGroupURL,VicubeCasesConfig.deleteGroupParams);
//        String result=String.valueOf(post);
//        String creator = InterceptFunction.intercept(result, "1");
//        AssertFunction.assertEquals(creator,"1");
//    }
//
//    @Test
//    public void getAllFunctions(){
//        Object post = httpFunction.post(VicubeCasesConfig.getAllFunctionsURL);
//        String result=String.valueOf(post);
//        String batch = InterceptFunction.intercept(result, "\"name\":\"新增\"");
//        AssertFunction.assertEquals(batch,"\"name\":\"新增\"");
//        String creator = InterceptFunction.intercept(result, "\"updateDate\":null");
//        AssertFunction.assertEquals(creator,"\"updateDate\":null");
//    }
//
//    @Test
//    public void findByModelName(){
//        Object post = httpFunction.post(VicubeCasesConfig.findByModelNameURL,VicubeCasesConfig.findByModelNameParams);
//        String result=String.valueOf(post);
//        String creator = InterceptFunction.intercept(result, "\"creator\":\"administrator\"");
//        AssertFunction.assertEquals(creator,"\"creator\":\"administrator\"");
//        String batch = InterceptFunction.intercept(result, "\"batch\":false");
//        AssertFunction.assertEquals(batch,"\"batch\":false");
//    }
//
//    @Test
//    public void queryObjectsByGroupId(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryObjectsByGroupIdURL,VicubeCasesConfig.queryObjectsByGroupIdParams);
//        String result=String.valueOf(post);
//        String creator = InterceptFunction.intercept(result, "\"domainName\":\"rootDomain\"");
//        AssertFunction.assertEquals(creator,"\"domainName\":\"rootDomain\"");
//    }
//
//    @Test
//    public void grantMenuAmoduleAview2User(){
//        Object post = httpFunction.post(VicubeCasesConfig.grantMenuAmoduleAview2UserURL,VicubeCasesConfig.grantMenuAmoduleAview2UserParams);
//        String result=String.valueOf(post);
//        String creator = InterceptFunction.intercept(result, "4");
//        AssertFunction.assertEquals(creator,"4");
//    }
//
//    @Test
//    public void grantObjectsDomain(){
//        Object post = httpFunction.post(VicubeCasesConfig.grantObjectsDomainURL,VicubeCasesConfig.grantObjectsDomainParams);
//        String result=String.valueOf(post);
//        String creator = InterceptFunction.intercept(result, "1");
//        AssertFunction.assertEquals(creator,"1");
//    }
//
//    @Test
//    public void queryUserInfo2(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryUserInfo2URL);
//        String result=String.valueOf(post);
//        String authMethod = InterceptFunction.intercept(result, "\"authMethod\":\"DB\"");
//        AssertFunction.assertEquals(authMethod,"\"authMethod\":\"DB\"");
//        String createDate = InterceptFunction.intercept(result, "\"createDate\":null");
//        AssertFunction.assertEquals(createDate,"\"createDate\":null");
//    }
//
//    @Test
//    public void queryAuditInfo(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryAuditInfoURL,VicubeCasesConfig.queryAuditInfoParams);
//        String result=String.valueOf(post);
//        String authMethod = InterceptFunction.intercept(result, "\"actionName\":\"删除工作组\"");
//        AssertFunction.assertEquals(authMethod,"\"actionName\":\"删除工作组\"");
//        String createDate = InterceptFunction.intercept(result, "\"auditId\":15589");
//        AssertFunction.assertEquals(createDate,"\"auditId\":15589");
//    }
//
//    @Test
//    public void insertBaseLinePolicy(){
//        Object post = httpFunction.post(VicubeCasesConfig.insertBaseLinePolicyURL,VicubeCasesConfig.insertBaseLinePolicyParams);
//        String result=String.valueOf(post);
//        String authMethod = InterceptFunction.intercept(result, "null");
//        AssertFunction.assertEquals(authMethod,"null");
//    }
//
//    @Test
//    public void getBaseLinePolicyList(){
//        Object post = httpFunction.post(VicubeCasesConfig.getBaseLinePolicyListURL,VicubeCasesConfig.getBaseLinePolicyListParams);
//        String result=String.valueOf(post);
//        String bottomLineDataProvider = InterceptFunction.intercept(result, "\"bottomLineDataProvider\":\"common\"");
//        AssertFunction.assertEquals(bottomLineDataProvider,"\"bottomLineDataProvider\":\"common\"");
//        String bottomLineDataRangeFrom = InterceptFunction.intercept(result, "\"bottomLineDataRangeFrom\":0");
//        AssertFunction.assertEquals(bottomLineDataRangeFrom,"\"bottomLineDataRangeFrom\":0");
//    }
//
//    @Test
//    public void updateBaseLinePolicy(){
//        Object post = httpFunction.post(VicubeCasesConfig.updateBaseLinePolicyURL,VicubeCasesConfig.updateBaseLinePolicyParams);
//        String result=String.valueOf(post);
//        String isNull = InterceptFunction.intercept(result, "null");
//        AssertFunction.assertEquals(isNull,"null");
//    }
//
//    @Test
//    public void cleanBaseLinePolicy(){
//        Object post = httpFunction.post(VicubeCasesConfig.cleanBaseLinePolicyURL,VicubeCasesConfig.cleanBaseLinePolicyParams);
//        String result=String.valueOf(post);
//        String isNull = InterceptFunction.intercept(result, "null");
//        AssertFunction.assertEquals(isNull,"null");
//    }
//
//    @Test
//    public void queryWarningRuleList(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryWarningRuleListURL,VicubeCasesConfig.queryWarningRuleListParams);
//        String result=String.valueOf(post);
//        String status = InterceptFunction.intercept(result, "\"status\":\"success\"");
//        AssertFunction.assertEquals(status,"\"status\":\"success\"");
//    }
//
//    @Test
//    public void getAllTypeNodes(){
//        Object post = httpFunction.post(VicubeCasesConfig.getAllTypeNodesURL,VicubeCasesConfig.getAllTypeNodesParams);
//        String result=String.valueOf(post);
//        String label = InterceptFunction.intercept(result, "\"label\":\"统一资源");
//        AssertFunction.assertEquals(label,"\"label\":\"统一资源");
//        String name = InterceptFunction.intercept(result, "\"name\":\"Ci\"");
//        AssertFunction.assertEquals(name,"\"name\":\"Ci\"");
//    }
//
//    @Test
//    public void getAllMyNodesByTypeName(){
//        Object post = httpFunction.post(VicubeCasesConfig.getAllMyNodesByTypeNameURL,VicubeCasesConfig.getAllMyNodesByTypeNameParams);
//        String result=String.valueOf(post);
//        String label = InterceptFunction.intercept(result, "[]");
//        AssertFunction.assertEquals(label,"[]");
//    }
//
//    @Test
//    public void getBaseLineDefList(){
//        Object post = httpFunction.post(VicubeCasesConfig.getBaseLineDefListURL,VicubeCasesConfig.getBaseLineDefListParams);
//        String result=String.valueOf(post);
//        String calendarType = InterceptFunction.intercept(result, "\"calendarType\":\"auto\"");
//        AssertFunction.assertEquals(calendarType,"\"calendarType\":\"auto\"");
//    }
//
//    @Test
//    public void getKpiItemList(){
//        Object post = httpFunction.post(VicubeCasesConfig.getKpiItemListURL,VicubeCasesConfig.getKpiItemListParams);
//        String result=String.valueOf(post);
//        String aggregate = InterceptFunction.intercept(result, "\"aggregate\":false");
//        AssertFunction.assertEquals(aggregate,"\"aggregate\":false");
//    }
//
//    @Test
//    public void getOneWarningRule(){
//        Object post = httpFunction.post(VicubeCasesConfig.getOneWarningRuleURL,VicubeCasesConfig.getOneWarningRuleParams);
//        String result=String.valueOf(post);
//        String status = InterceptFunction.intercept(result, "\"status\":\"success\"");
//        AssertFunction.assertEquals(status,"\"status\":\"success\"");
//    }
//
//    @Test
//    public void checkBaseLineDef(){
//        Object post = httpFunction.post(VicubeCasesConfig.checkBaseLineDefURL,VicubeCasesConfig.checkBaseLineDefParams);
//        String result=String.valueOf(post);
//        String isTrue = InterceptFunction.intercept(result, "true");
//        AssertFunction.assertEquals(isTrue,"true");
//    }
//
//    @Test
//    public void insertBaseLineDef(){
//        Object post = httpFunction.post(VicubeCasesConfig.insertBaseLineDefURL,VicubeCasesConfig.insertBaseLineDefParams);
//        String result=String.valueOf(post);
//        String calendarType = InterceptFunction.intercept(result, "\"calendarType\":\"auto\"");
//        AssertFunction.assertEquals(calendarType,"\"calendarType\":\"auto\"");
//    }
//
//    @Test
//    public void updateBaseLineCalendarByBatch(){
//        Object post = httpFunction.post(VicubeCasesConfig.updateBaseLineCalendarByBatchURL,VicubeCasesConfig.updateBaseLineCalendarByBatchParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "success");
//        AssertFunction.assertEquals(success,"success");
//    }
//
//    @Test
//    public void getBaseLineCalendarList(){
//        Object post = httpFunction.post(VicubeCasesConfig.getBaseLineCalendarListURL,VicubeCasesConfig.getBaseLineCalendarListParams);
//        String result=String.valueOf(post);
//        String cycleType = InterceptFunction.intercept(result, "[]");
//        AssertFunction.assertEquals(cycleType,"[]");
//    }
//
//    @Test
//    public void cleanBaseLineDef(){
//        Object post = httpFunction.post(VicubeCasesConfig.cleanBaseLineDefURL,VicubeCasesConfig.cleanBaseLineDefParams);
//        String result=String.valueOf(post);
//        String isNull = InterceptFunction.intercept(result, "null");
//        AssertFunction.assertEquals(isNull,"null");
//    }
//
//    @Test
//    public void findModelTree(){
//        Object post = httpFunction.post(VicubeCasesConfig.findModelTreeURL,VicubeCasesConfig.findModelTreeParams);
//        String result=String.valueOf(post);
//        String attributes = InterceptFunction.intercept(result, "\"attributes\":[]");
//        AssertFunction.assertEquals(attributes,"\"attributes\":[]");
//    }
//
//    @Test
//    public void getEnums(){
//        Object post = httpFunction.post(VicubeCasesConfig.getEnumsURL,VicubeCasesConfig.getEnumsParams);
//        String result=String.valueOf(post);
//        String attributes = InterceptFunction.intercept(result, "[\"EQ\",\"NE\",\"LIKE\",\"LT\",\"GT\",\"LE\",\"GE\",\"IN\",\"NOT_IN\"]");
//        AssertFunction.assertEquals(attributes,"[\"EQ\",\"NE\",\"LIKE\",\"LT\",\"GT\",\"LE\",\"GE\",\"IN\",\"NOT_IN\"]");
//    }
//
//
//    @Test
//    public void findInstanceByModelSortOrder(){
//        Object post = httpFunction.post(VicubeCasesConfig.findInstanceByModelSortOrderURL,VicubeCasesConfig.findInstanceByModelSortOrderParams);
//        String result=String.valueOf(post);
//        String batch = InterceptFunction.intercept(result, "\"batch\":false");
//        AssertFunction.assertEquals(batch,"\"batch\":false");
//    }
//
//    @Test
//    public void getAllDicts(){
//        Object post = httpFunction.post(VicubeCasesConfig.getAllDictsURL);
//        String result=String.valueOf(post);
//        String code = InterceptFunction.intercept(result, "\"code\":\"YesorNo\"");
//        AssertFunction.assertEquals(code,"\"code\":\"YesorNo\"");
//    }
//
//    @Test
//    public void queryByFilter(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryByFilterURL,VicubeCasesConfig.queryByFilterParams);
//        String result=String.valueOf(post);
//        String data = InterceptFunction.intercept(result, "{\"data\":[],\"size\":0}");
//        AssertFunction.assertEquals(data,"{\"data\":[],\"size\":0}");
//    }
//
//    @Test
//    public void findAll(){
//        Object post = httpFunction.post(VicubeCasesConfig.findAllURL);
//        String result=String.valueOf(post);
//        String attributes = InterceptFunction.intercept(result, "\"attributes\":[]");
//        AssertFunction.assertEquals(attributes,"\"attributes\":[]");
//    }
//
//    @Test
//    public void findSelectTree(){
//        Object post = httpFunction.post(VicubeCasesConfig.findSelectTreeURL,VicubeCasesConfig.findSelectTreeParams);
//        String result=String.valueOf(post);
//        String attributes = InterceptFunction.intercept(result, "\"attributes\":[]");
//        AssertFunction.assertEquals(attributes,"\"attributes\":[]");
//    }
//
//    @Test
//    public void findByOperationTypeAndModelName(){
//        Object post = httpFunction.post(VicubeCasesConfig.findByOperationTypeAndModelNameURL,VicubeCasesConfig.findByOperationTypeAndModelNameParams);
//        String result=String.valueOf(post);
//        String recordsTotal = InterceptFunction.intercept(result, "\"recordsTotal\":0");
//        AssertFunction.assertEquals(recordsTotal,"\"recordsTotal\":0");
//    }
//
//    @Test
//    public void find(){
//        Object post = httpFunction.post(VicubeCasesConfig.findURL,VicubeCasesConfig.findParams);
//        String result=String.valueOf(post);
//        String recordsTotal = InterceptFunction.intercept(result, "\"recordsTotal\":0");
//        AssertFunction.assertEquals(recordsTotal,"\"recordsTotal\":0");
//    }
//
//    @Test
//    public void queryByFilter2(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryByFilter2URL,VicubeCasesConfig.queryByFilter2Params);
//        String result=String.valueOf(post);
//        String attributes = InterceptFunction.intercept(result, "\"attributes\":[]");
//        AssertFunction.assertEquals(attributes,"\"attributes\":[]");
//    }
//    private static CloseableHttpClient httpClient;
//    private static CloseableHttpResponse response;
//
//    @Test
//    public void findByNameNotBuiltInAttr(){
//        Object post = httpFunction.post(VicubeCasesConfig.findByNameNotBuiltInAttrURL,VicubeCasesConfig.findByNameNotBuiltInAttrParams);
//        String result=String.valueOf(post);
//        String attributes = InterceptFunction.intercept(result, "\"attributes\":[]");
//        AssertFunction.assertEquals(attributes,"\"attributes\":[]");
//    }
//    @Test
//    public void add(){
//        Object post = httpFunction.post(VicubeCasesConfig.addURL,VicubeCasesConfig.addParams);
//        String result=String.valueOf(post);
//        String attributes = InterceptFunction.intercept(result, "\"attributes\":[]");
//        AssertFunction.assertEquals(attributes,"\"attributes\":[]");
//    }
//
//    @Test
//    public void update(){
//        Object post = httpFunction.post(VicubeCasesConfig.updateURL,VicubeCasesConfig.updateParams);
//        String result=String.valueOf(post);
//        String attributes = InterceptFunction.intercept(result, "\"attributes\":[]");
//        AssertFunction.assertEquals(attributes,"\"attributes\":[]");
//    }
//
//    @Test
//    public void remove(){
//        Object post = httpFunction.post(VicubeCasesConfig.removeURL,VicubeCasesConfig.removeParams);
//        String result=String.valueOf(post);
//        AssertFunction.assertEquals(result,"");
//    }
//
//    @Test
//    public void relationAttributeServiceAdd(){
//        Object post = httpFunction.post(VicubeCasesConfig.relationAttributeServiceAddURL,VicubeCasesConfig.relationAttributeServiceAddParams);
//        String result=String.valueOf(post);
//        String allowInheritance = InterceptFunction.intercept(result, "\"allowInheritance\":false");
//        AssertFunction.assertEquals(allowInheritance,"\"allowInheritance\":false");
//    }
//
//    @Test
//    public void findById(){
//        Object post = httpFunction.post(VicubeCasesConfig.findByIdURL,VicubeCasesConfig.findByIdParams);
//        String result=String.valueOf(post);
//        String alertMessage = InterceptFunction.intercept(result, "\"alertMessage\":\"\"");
//        AssertFunction.assertEquals(alertMessage,"\"alertMessage\":\"\"");
//    }
//
//    @Test
//    public void removeBatch(){
//        Object post = httpFunction.post(VicubeCasesConfig.removeBatchURL,VicubeCasesConfig.removeBatchParams);
//        String result=String.valueOf(post);
//        AssertFunction.assertEquals(result,"");
//    }
//
//    @Test
//    public void findList(){
//        Object post = httpFunction.post(VicubeCasesConfig.findListURL,VicubeCasesConfig.findListParams);
//        String result=String.valueOf(post);
//        String alertMessage = InterceptFunction.intercept(result, "[]");
//        AssertFunction.assertEquals(result,"[]");
//    }
//
//    @Test
//    public void instanceHistoryServiceFind(){
//        Object post = httpFunction.post(VicubeCasesConfig.instanceHistoryServiceFindURL,VicubeCasesConfig.instanceHistoryServiceFindParams);
//        String result=String.valueOf(post);
//        String draw = InterceptFunction.intercept(result, "\"recordsTotal\":0,\"recordsFiltered\":0");
//        AssertFunction.assertEquals(draw,"\"recordsTotal\":0,\"recordsFiltered\":0");
//    }
//
//    @Test
//    public void queryFilterByColorAdmin(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryFilterByColorAdminURL,VicubeCasesConfig.queryFilterByColorAdminParams);
//        String result=String.valueOf(post);
//        String draw = InterceptFunction.intercept(result, "\"recordsTotal\":0,\"recordsFiltered\":0");
//        AssertFunction.assertEquals(draw,"\"recordsTotal\":0,\"recordsFiltered\":0");
//    }
//
//    @Test
//    public void getDefulateValue(){
//        Object post = httpFunction.post(VicubeCasesConfig.getDefulateValueURL,VicubeCasesConfig.getDefulateValueParams);
//        String result=String.valueOf(post);
//        String draw = InterceptFunction.intercept(result, "\"batch\":false");
//        AssertFunction.assertEquals(draw,"\"batch\":false");
//    }
//
//    @Test
//    public void queryInstanceByRelationPage(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryInstanceByRelationPageURL,VicubeCasesConfig.queryInstanceByRelationPageParams);
//        String result=String.valueOf(post);
//        String data = InterceptFunction.intercept(result, "\"data\":[],\"size\":0");
//        AssertFunction.assertEquals(data,"\"data\":[],\"size\":0");
//    }
//    @Test
//    public void validation(){
//        Object post = httpFunction.post(VicubeCasesConfig.validationURL,VicubeCasesConfig.validationParams);
//        String result=String.valueOf(post);
//        String data = InterceptFunction.intercept(result, "true");
//        AssertFunction.assertEquals(data,"true");
//    }
//
//    @Test
//    public void instanceServiceAdd(){
//        Object post = httpFunction.post(VicubeCasesConfig.instanceServiceAddURL,VicubeCasesConfig.instanceServiceAddParams);
//        String result=String.valueOf(post);
//        String batch = InterceptFunction.intercept(result, "\"batch\":false");
//        AssertFunction.assertEquals(batch,"\"batch\":false");
//    }
//
//    @Test
//    public void instanceServiceFindById(){
//        Object post = httpFunction.post(VicubeCasesConfig.instanceServiceFindByIdURL,VicubeCasesConfig.instanceServiceFindByIdParams);
//        String result=String.valueOf(post);
//        String batch = InterceptFunction.intercept(result, "\"batch\":false");
//        AssertFunction.assertEquals(batch,"\"batch\":false");
//    }
//
//    @Test
//    public void findByName(){
//        Object post = httpFunction.post(VicubeCasesConfig.findByNameURL,VicubeCasesConfig.findByNameParams);
//        String result=String.valueOf(post);
//        String batch = InterceptFunction.intercept(result, "\"alertMessage\":\"\"");
//        AssertFunction.assertEquals(batch,"\"alertMessage\":\"\"");
//    }
//
//    @Test
//    public void findBySourceOrTarget_Label(){
//        Object post = httpFunction.post(VicubeCasesConfig.findBySourceOrTarget_LabelURL,VicubeCasesConfig.findBySourceOrTarget_LabelParams);
//        String result=String.valueOf(post);
//        String batch = InterceptFunction.intercept(result, "[]");
//        AssertFunction.assertEquals(batch,"[]");
//    }
//
//    @Test
//    public void findBySourceOrTarget(){
//        Object post = httpFunction.post(VicubeCasesConfig.findBySourceOrTargetURL,VicubeCasesConfig.findBySourceOrTargetParams);
//        String result=String.valueOf(post);
//        String batch = InterceptFunction.intercept(result, "[]");
//        AssertFunction.assertEquals(batch,"[]");
//    }
//
//    @Test
//    public void instanceServiceUpdate(){
//        Object post = httpFunction.post(VicubeCasesConfig.instanceServiceUpdateURL,VicubeCasesConfig.instanceServiceUpdateParams);
//        String result=String.valueOf(post);
//        String batch = InterceptFunction.intercept(result, "\"batch\":false");
//        AssertFunction.assertEquals(batch,"\"batch\":false");
//    }
//
//    @Test
//    public void checkRelation(){
//        Object post = httpFunction.post(VicubeCasesConfig.checkRelationURL,VicubeCasesConfig.checkRelationParams);
//        String result=String.valueOf(post);
//        String batch = InterceptFunction.intercept(result, "\"\"");
//        AssertFunction.assertEquals(batch,"\"\"");
//    }
//
//    @Test
//    public void checkInstanceHasRelation(){
//        Object post = httpFunction.post(VicubeCasesConfig.checkInstanceHasRelationURL,VicubeCasesConfig.checkInstanceHasRelationParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "{\"success\":false}");
//        AssertFunction.assertEquals(success,"{\"success\":false}");
//    }
//
//    @Test
//    public void instanceServiceRemove(){
//        Object post = httpFunction.post(VicubeCasesConfig.instanceServiceRemoveURL,VicubeCasesConfig.instanceServiceRemoveParams);
//        String result=String.valueOf(post);
//        AssertFunction.assertEquals(result,"");
//    }
//
//    @Test
//    public void waitForConfirmationCount(){
//        Object post = httpFunction.post(VicubeCasesConfig.waitForConfirmationCountURL);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"status\":\"success\"");
//        AssertFunction.assertEquals(success,"\"status\":\"success\"");
//    }
//
//    @Test
//    public void confirmationedCount(){
//        Object post = httpFunction.post(VicubeCasesConfig.confirmationedCountURL);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"data\":3");
//        AssertFunction.assertEquals(success,"\"data\":3");
//    }
//
//    @Test
//    public void getWaitForConfirmationList(){
//        Object post = httpFunction.post(VicubeCasesConfig.getWaitForConfirmationListURL,VicubeCasesConfig.getWaitForConfirmationListParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"recordsTotal\":0,\"recordsFiltered\":0");
//        AssertFunction.assertEquals(success,"\"recordsTotal\":0,\"recordsFiltered\":0");
//    }
//
//    @Test
//    public void getConfirmationedList(){
//        Object post = httpFunction.post(VicubeCasesConfig.getConfirmationedListURL,VicubeCasesConfig.getConfirmationedListParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"recordsTotal\":0,\"recordsFiltered\":0");
//        AssertFunction.assertEquals(success,"\"recordsTotal\":0,\"recordsFiltered\":0");
//    }
//
//    @Test
//    public void getUrmpTreeByKeyFilter(){
//        Object post = httpFunction.post(VicubeCasesConfig.getUrmpTreeByKeyFilterURL,VicubeCasesConfig.getUrmpTreeByKeyFilterParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"batch\":false");
//        AssertFunction.assertEquals(success,"\"batch\":false");
//    }
//
//    @Test
//    public void statisticAlertCountByStateCache(){
//        Object post = httpFunction.post(VicubeCasesConfig.statisticAlertCountByStateCacheURL,VicubeCasesConfig.statisticAlertCountByStateCacheParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "20");
//        AssertFunction.assertEquals(success,"20");
//    }
//
//    @Test
//    public void queryIncidentType(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryIncidentTypeURL);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"comments\":\"\"");
//        AssertFunction.assertEquals(success,"\"comments\":\"\"");
//    }
//
//    @Test
//    public void queryFullUserInfo(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryFullUserInfoURL);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"authMethod\":\"DB\"");
//        AssertFunction.assertEquals(success,"\"authMethod\":\"DB\"");
//    }
//
//    @Test
//    public void getForwardTypeMap(){
//        Object post = httpFunction.post(VicubeCasesConfig.getForwardTypeMapURL);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"label\":\"运维系统\"");
//        AssertFunction.assertEquals(success,"\"label\":\"运维系统\"");
//    }
//
//    @Test
//    public void getGlobalModule(){
//        Object post = httpFunction.post(VicubeCasesConfig.getGlobalModuleURL,VicubeCasesConfig.getGlobalModuleParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"status\":\"success\"");
//        AssertFunction.assertEquals(success,"\"status\":\"success\"");
//    }
//
//    @Test
//    public void countAlertFromCacheForHtmlData(){
//        Object post = httpFunction.post(VicubeCasesConfig.countAlertFromCacheForHtmlDataURL,VicubeCasesConfig.countAlertFromCacheForHtmlDataParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"3\":2");
//        AssertFunction.assertEquals(success,"\"3\":2");
//    }
//
//    @Test
//    public void queryAlertFromCacheForHtmlData(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryAlertFromCacheForHtmlDataURL,VicubeCasesConfig.queryAlertFromCacheForHtmlDataParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "recordsFiltered");
//        AssertFunction.assertEquals(success,"recordsFiltered");
//    }
//
//    @Test
//    public void sendAlertClaimAction(){
//        Object post = httpFunction.post(VicubeCasesConfig.sendAlertClaimActionURL,VicubeCasesConfig.sendAlertClaimActionParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "actTime");
//        AssertFunction.assertEquals(success,"actTime");
//    }
//
//    @Test
//    public void sendForwordAction(){
//        Object post = httpFunction.post(VicubeCasesConfig.sendForwordActionURL,VicubeCasesConfig.sendForwordActionParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "actTime");
//        AssertFunction.assertEquals(success,"actTime");
//    }
//
//    @Test
//    public void sendAlertRecoverAction(){
//        Object post = httpFunction.post(VicubeCasesConfig.sendAlertRecoverActionURL,VicubeCasesConfig.sendAlertRecoverActionParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "actTime");
//        AssertFunction.assertEquals(success,"actTime");
//    }
//
//    @Test
//    public void getNodeByName(){
//        Object post = httpFunction.post(VicubeCasesConfig.getNodeByNameURL,VicubeCasesConfig.getNodeByNameParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"name\":\"Switch\"");
//        AssertFunction.assertEquals(success,"\"name\":\"Switch\"");
//    }
//
//    @Test
//    public void queryEvent(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryEventURL,VicubeCasesConfig.queryEventParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"agentId\":\"XM_XBROTHER\"");
//        AssertFunction.assertEquals(success,"\"agentId\":\"XM_XBROTHER\"");
//    }
//
//    @Test
//    public void queryActionLog(){
//        Object post = httpFunction.post(VicubeCasesConfig.queryActionLogURL,VicubeCasesConfig.queryActionLogParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "actTime");
//        AssertFunction.assertEquals(success,"actTime");
//    }
//
//    @Test
//    public void getRelatedAlerts(){
//        Object post = httpFunction.post(VicubeCasesConfig.getRelatedAlertsURL,VicubeCasesConfig.getRelatedAlertsParams);
//        String result=String.valueOf(post);
//        String success = InterceptFunction.intercept(result, "\"activeState\":0");
//        AssertFunction.assertEquals(success,"\"activeState\":0");
//    }
//
//
//
//
//}
