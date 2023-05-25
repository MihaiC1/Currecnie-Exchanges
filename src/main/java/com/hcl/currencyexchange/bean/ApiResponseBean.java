package com.hcl.currencyexchange.bean;

import lombok.Data;



/**
 * <h1>Api Response Bean class</h1>
 * The ApiResponseBean class is used to store the data received when making a request to the API.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */
@Data
public class ApiResponseBean {

    private DataBean dataBean;

    /**
     * This is the parametrized constructor of the class.
     * @param dataBean The DataBean object.
     */
    public ApiResponseBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    /**
     * This is the empty constructor.
     */
    public ApiResponseBean() {

    }

    /**
     * This method is used to get the private field dataBean
     * @return DataBean A DataBean object
     */
    public DataBean getData() {
        return dataBean;
    }

    /**
     * This method is used to set the dataBean property
     * @param dataBean The object that will be saved as the dataBean property
     */
    public void setData(DataBean dataBean) {
        this.dataBean = dataBean;
    }

}
