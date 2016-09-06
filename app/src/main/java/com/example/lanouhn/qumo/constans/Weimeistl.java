package com.example.lanouhn.qumo.constans;

/**
 * Created by lanouhn on 2016/8/25.
 */
public class Weimeistl {
    private String content;//标题
    private String comment_count;//评论
    private String share_count;//分享
    private String bury_count;//鄙视
    private String digg_count;//赞


    private user user;

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getShare_count() {
        return share_count;
    }

    public void setShare_count(String share_count) {
        this.share_count = share_count;
    }

    public String getBury_count() {
        return bury_count;
    }

    public void setBury_count(String bury_count) {
        this.bury_count = bury_count;
    }

    public String getDigg_count() {
        return digg_count;
    }

    public void setDigg_count(String digg_count) {
        this.digg_count = digg_count;
    }

    public static final class user {
        private String name;
        private String avatar_url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }
    }


    private large_image large_image;//图片

    public Weimeistl.large_image getLarge_image() {
        return large_image;
    }

    public void setLarge_image(Weimeistl.large_image large_image) {
        this.large_image = large_image;
    }

    public static final class large_image {
        private list_url list_url;
        private String url;//图片

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public list_url getList_url() {
            return list_url;
        }

        public void setList_url(list_url list_url) {
            this.list_url = list_url;
        }

        public static final class list_url {
            private String url;//图片

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
