package com.zx.okhttp.requestimpl;


import com.zx.okhttp.api.ApiRequest;
import com.zx.okhttp.env.ObjectTool;
import com.zx.okhttp.result.NewGameResponse;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/30.
 */
public class GetGamesRequest<T extends NewGameResponse> extends ApiRequest<NewGameResponse> {


    @Override
    protected String getMethod() {
        return "111";
    }

    public static class GetGamesParam implements Serializable {
        @ObjectTool.ObjectTag("limit")
        private int limit;
        @ObjectTool.ObjectTag("page")
        private int page;

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }
}
