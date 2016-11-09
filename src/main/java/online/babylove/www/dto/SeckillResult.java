package online.babylove.www.dto;

/**
 * Created by Will.Zhang on 2016/11/7 0007 16:19.
 */

/**
 * 所有ajax请求的返回类型
 * 封装Json结果
 * @param <T>
 */
public class SeckillResult<T> {

    //是否成功
    private boolean success;

    //封装的数据
    private T data;

    //错误描述
    private String error;

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
