package com.fgo.utils.bean;

/**
 * Created by gkq on 2017/11/9.
 * 公共基类
 */
public class BaseCommonBean<T> {

    private String respCode;//响应吗
    private String respMsg;//响应信息

    private BaseCommonData<T> data;//响应数据

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        respMsg = respMsg;
    }

    public BaseCommonData getData() {
        return data;
    }

    public void setData(BaseCommonData data) {
        this.data = data;
    }




    /**
     * 响应数据
     *
     * @param <T> 参数值
     */
    public class BaseCommonData<T> {


        private T model;//model数据
        private java.util.List<T> list;//集合数据
        private String result;//响应信息

        public T getModel() {
            return model;
        }

        public void setModel(T model) {
            model = model;
        }

        public java.util.List<T> getList() {
            return list;
        }

        public void setList(java.util.List<T> list) {
            list = list;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            result = result;
        }


    }


}
