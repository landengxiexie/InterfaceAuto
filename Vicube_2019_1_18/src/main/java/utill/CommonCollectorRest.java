//package utill;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import org.apache.log4j.Logger;
//
//import com.rj.itis.core2.procengine.ProcEngine;
//import com.ws.restcenter.common.custom.CustomResponse;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
///**
//* <p>Title: CommonRest.java</p>
//* <p>Description: </p>
//* @author wwei
//* @date 2018年8月9日
//*/
//
//@SuppressWarnings("ALL")
//@Path("/v1/collector")
//public class CommonCollectorRest {
//
//	private Logger log = Logger.getLogger(InnerKpiRest.class);
//
//	@GET
//    @Path("testHello")
//    @Produces("text/plain")
//    public String testHello(@Context HttpServletRequest request){
//		String name = "welcome! apirestcenter";
//		log.info(" --- /v1/collector: welcome! apirestcenter --- ");
//
//        return name;
//    }
//
//	/**
//	 * 通过POST方式推送数据
//	 * @param data
//	 * @param convertName
//	 * @return
//	 */
//	@POST
//	@Path("/postArrayData/{convertName}")
//	@Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//	public CustomResponse postArrayData( JSONArray data, @PathParam("convertName") String convertName ) {
//		CustomResponse res = new CustomResponse();
//		try {
//			if (data == null || "".equals(data)) {
//				log.error("The Data is NULL.  convertName:"+convertName);
//				res.failure("The Data is NULL.");
//				return res;
//			}
//
//			if (data.isEmpty() || data.size()==0 || "".equals(data.toString()) || "[null]".equals(data.toString())) {
//				log.error("The Data is Empty. ");
//				res.failure("The Data is Empty.");
//				return res;
//			}
//
//			if (log.isDebugEnabled()) {
//				log.debug("InnerKpiRest.sendNewKpi>>>>>>>>>>>>>>>>");
//				log.debug("convertName:"+convertName);;
//				log.debug("data:"+data.toString());
//			}
//
//			// 数据组装方式  convertName|xxxxxxxxxxx
//			if (log.isDebugEnabled()) {
//				log.debug("****************** call dispatch name："+convertName);
//				log.debug("****************** call dispatch value："+data.toString());
//			}
//			ProcEngine.instance().dispatch(data.toString(), convertName);
//
//			res.success("postData SUCCESS.");
//			if (log.isDebugEnabled()) {
//				log.debug("dispatch All data success!!!!");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(e.getMessage(), e);
//			res.failure(e.getMessage().replaceAll("\"", " "));
//			return res;
//		}
//
//		return res;
//	}
//
//	/**
//	 * 通过POST方式推送数据
//	 * @param data
//	 * @param convertName
//	 * @return
//	 */
//	@POST
//	@Path("/postSingleData/{convertName}")
//	@Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//	public CustomResponse postSingleData( JSONArray data, @PathParam("convertName") String convertName ) {
//		CustomResponse res = new CustomResponse();
//		try {
//			if (data == null || "".equals(data)) {
//				log.error("The Data is NULL.  convertName:"+convertName);
//				res.failure("The Data is NULL.");
//				return res;
//			}
//
//			if (data.isEmpty() || data.size()==0 || "".equals(data.toString()) || "[null]".equals(data.toString())) {
//				log.error("The Data is Empty. ");
//				res.failure("The Data is Empty.");
//				return res;
//			}
//
//			if (log.isDebugEnabled()) {
//				log.debug("InnerKpiRest.sendNewKpi>>>>>>>>>>>>>>>>");
//				log.debug("convertName:"+convertName);;
//				log.debug("data:"+data.toString());
//			}
//
//			// 数据组装方式  convertName|xxxxxxxxxxx
//			if (log.isDebugEnabled()) {
//				log.debug("****************** call dispatch name："+convertName);
//				log.debug("****************** call dispatch value："+data.toString());
//			}
//			ProcEngine.instance().dispatch(data.toString(), convertName);
//
//			res.success("postData SUCCESS.");
//			if (log.isDebugEnabled()) {
//				log.debug("dispatch All data success!!!!");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(e.getMessage(), e);
//			res.failure(e.getMessage().replaceAll("\"", " "));
//			return res;
//		}
//
//		return res;
//	}
//}
