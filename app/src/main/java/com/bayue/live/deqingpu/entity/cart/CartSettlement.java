package com.bayue.live.deqingpu.entity.cart;

import java.util.List;

/**
 * Created by BaYue on 2017/6/28 0028.
 * email : 2651742485@qq.com
 */

public class CartSettlement {

    /**
     * code : 200
     * data : {"favourable":[],"goods":[{"goods_count_number":11,"goods_count_price":200,"goods_info":[{"goods_attr":[{"attr":"款式:女","goods_attr_id":864},{"attr":"材质:红18k金","goods_attr_id":867},{"attr":"款式:男","goods_attr_id":996}],"goods_attr_id":[],"goods_id":"54","goods_name":"测试商品","goods_number":4,"goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","market_price":"120.00","shop_price":"503.00","store_id":1}],"merchant_name":"我是掌柜","store_id":1}],"goods_price":1100,"pay_price":4325,"shipping_free":0,"user_address":{"address":"5和1365","address_id":16,"city":4,"city_name":"","consignee":"卡通","district":398,"district_name":"迎江区","mobile":"15121019945","province":3,"province_name":"安徽","user_id":26}}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * favourable : []
         * goods : [{"goods_count_number":11,"goods_count_price":200,"goods_info":[{"goods_attr":[{"attr":"款式:女","goods_attr_id":864},{"attr":"材质:红18k金","goods_attr_id":867},{"attr":"款式:男","goods_attr_id":996}],"goods_attr_id":[],"goods_id":"54","goods_name":"测试商品","goods_number":4,"goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","market_price":"120.00","shop_price":"503.00","store_id":1}],"merchant_name":"我是掌柜","store_id":1}]
         * goods_price : 1100
         * pay_price : 4325
         * shipping_free : 0
         * user_address : {"address":"5和1365","address_id":16,"city":4,"city_name":"","consignee":"卡通","district":398,"district_name":"迎江区","mobile":"15121019945","province":3,"province_name":"安徽","user_id":26}
         */

        private int goods_price;
        private int pay_price;
        private int shipping_free;
        private UserAddressBean user_address;
        private List<?> favourable;
        private List<GoodsBean> goods;

        public int getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(int goods_price) {
            this.goods_price = goods_price;
        }

        public int getPay_price() {
            return pay_price;
        }

        public void setPay_price(int pay_price) {
            this.pay_price = pay_price;
        }

        public int getShipping_free() {
            return shipping_free;
        }

        public void setShipping_free(int shipping_free) {
            this.shipping_free = shipping_free;
        }

        public UserAddressBean getUser_address() {
            return user_address;
        }

        public void setUser_address(UserAddressBean user_address) {
            this.user_address = user_address;
        }

        public List<?> getFavourable() {
            return favourable;
        }

        public void setFavourable(List<?> favourable) {
            this.favourable = favourable;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class UserAddressBean {
            /**
             * address : 5和1365
             * address_id : 16
             * city : 4
             * city_name :
             * consignee : 卡通
             * district : 398
             * district_name : 迎江区
             * mobile : 15121019945
             * province : 3
             * province_name : 安徽
             * user_id : 26
             */

            private String address;
            private int address_id;
            private int city;
            private String city_name;
            private String consignee;
            private int district;
            private String district_name;
            private String mobile;
            private int province;
            private String province_name;
            private int user_id;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getAddress_id() {
                return address_id;
            }

            public void setAddress_id(int address_id) {
                this.address_id = address_id;
            }

            public int getCity() {
                return city;
            }

            public void setCity(int city) {
                this.city = city;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public int getDistrict() {
                return district;
            }

            public void setDistrict(int district) {
                this.district = district;
            }

            public String getDistrict_name() {
                return district_name;
            }

            public void setDistrict_name(String district_name) {
                this.district_name = district_name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getProvince() {
                return province;
            }

            public void setProvince(int province) {
                this.province = province;
            }

            public String getProvince_name() {
                return province_name;
            }

            public void setProvince_name(String province_name) {
                this.province_name = province_name;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }
        }

        public static class GoodsBean {
            /**
             * goods_count_number : 11
             * goods_count_price : 200
             * goods_info : [{"goods_attr":[{"attr":"款式:女","goods_attr_id":864},{"attr":"材质:红18k金","goods_attr_id":867},{"attr":"款式:男","goods_attr_id":996}],"goods_attr_id":[],"goods_id":"54","goods_name":"测试商品","goods_number":4,"goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","market_price":"120.00","shop_price":"503.00","store_id":1}]
             * merchant_name : 我是掌柜
             * store_id : 1
             */

            private int goods_count_number;
            private int goods_count_price;
            private String merchant_name;
            private int store_id;
            private List<GoodsInfoBean> goods_info;

            public int getGoods_count_number() {
                return goods_count_number;
            }

            public void setGoods_count_number(int goods_count_number) {
                this.goods_count_number = goods_count_number;
            }

            public int getGoods_count_price() {
                return goods_count_price;
            }

            public void setGoods_count_price(int goods_count_price) {
                this.goods_count_price = goods_count_price;
            }

            public String getMerchant_name() {
                return merchant_name;
            }

            public void setMerchant_name(String merchant_name) {
                this.merchant_name = merchant_name;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public List<GoodsInfoBean> getGoods_info() {
                return goods_info;
            }

            public void setGoods_info(List<GoodsInfoBean> goods_info) {
                this.goods_info = goods_info;
            }

            public static class GoodsInfoBean {
                /**
                 * goods_attr : [{"attr":"款式:女","goods_attr_id":864},{"attr":"材质:红18k金","goods_attr_id":867},{"attr":"款式:男","goods_attr_id":996}]
                 * goods_attr_id : []
                 * goods_id : 54
                 * goods_name : 测试商品
                 * goods_number : 4
                 * goods_thumb : https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg
                 * market_price : 120.00
                 * shop_price : 503.00
                 * store_id : 1
                 */

                private String goods_id;
                private String goods_name;
                private int goods_number;
                private String goods_thumb;
                private String market_price;
                private String shop_price;
                private int store_id;
                private List<GoodsAttrBean> goods_attr;
//                private List<?> goods_attr_id;

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public int getGoods_number() {
                    return goods_number;
                }

                public void setGoods_number(int goods_number) {
                    this.goods_number = goods_number;
                }

                public String getGoods_thumb() {
                    return goods_thumb;
                }

                public void setGoods_thumb(String goods_thumb) {
                    this.goods_thumb = goods_thumb;
                }

                public String getMarket_price() {
                    return market_price;
                }

                public void setMarket_price(String market_price) {
                    this.market_price = market_price;
                }

                public String getShop_price() {
                    return shop_price;
                }

                public void setShop_price(String shop_price) {
                    this.shop_price = shop_price;
                }

                public int getStore_id() {
                    return store_id;
                }

                public void setStore_id(int store_id) {
                    this.store_id = store_id;
                }

                public List<GoodsAttrBean> getGoods_attr() {
                    return goods_attr;
                }

                public void setGoods_attr(List<GoodsAttrBean> goods_attr) {
                    this.goods_attr = goods_attr;
                }

//                public List<?> getGoods_attr_id() {
//                    return goods_attr_id;
//                }
//
//                public void setGoods_attr_id(List<?> goods_attr_id) {
//                    this.goods_attr_id = goods_attr_id;
//                }

                public static class GoodsAttrBean {
                    /**
                     * attr : 款式:女
                     * goods_attr_id : 864
                     */

                    private String attr;
                    private int goods_attr_id;

                    public String getAttr() {
                        return attr;
                    }

                    public void setAttr(String attr) {
                        this.attr = attr;
                    }

                    public int getGoods_attr_id() {
                        return goods_attr_id;
                    }

                    public void setGoods_attr_id(int goods_attr_id) {
                        this.goods_attr_id = goods_attr_id;
                    }
                }
            }
        }
    }
}
