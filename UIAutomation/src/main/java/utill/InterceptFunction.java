package utill;

public class InterceptFunction {
    public static String intercept(String result,String interceptString){
        try {
            int i = result.indexOf(interceptString);
            int length = interceptString.length();
            String interceptResult = result.substring(i, i + length);
//        LogFunction.logInfo("截取字符串的值为："+interceptResult);
            return interceptResult;
        }catch (Exception e){
            LogFunction.logError("字符串截取失败--"+"\""+interceptString+"\""+"--不存在");
            throw new RuntimeException("字符串截取失败--"+"\""+interceptString+"\""+"--不存在");
        }
    }
}
