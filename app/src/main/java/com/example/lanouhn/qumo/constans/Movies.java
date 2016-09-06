package com.example.lanouhn.qumo.constans;

/**
 * Created by lanouhn on 2016/8/18.
 * 情感视频实体类
 */
public class Movies {
  private String content;//标题
    private String comment_count;//评论
    private String share_count;//分享
    private String bury_count;//鄙视
    private String digg_count;//赞
    private String url_mp4;

    private user user;//内部类

    public Movies.user getUser() {
        return user;
    }

    public void setUser(Movies.user user) {
        this.user = user;
    }

    private large_cover large_cover;//图片内部类

    public Movies.large_cover getLarge_cover() {
        return large_cover;
    }

    public void setLarge_cover(Movies.large_cover large_cover) {
        this.large_cover = large_cover;
    }

    public String getUrl_mp4() {
        return url_mp4;
    }

    public void setUrl_mp4(String url_mp4) {
        this.url_mp4 = url_mp4;
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



  public  static final class large_cover{
     private url_list url_list;
      private String url;//图片

      public String getUrl() {
          return url;
      }

      public void setUrl(String url) {
          this.url = url;
      }

      public Movies.large_cover.url_list getUrl_list() {
          return url_list;
      }

      public void setUrl_list(Movies.large_cover.url_list url_list) {
          this.url_list = url_list;
      }

      public static  final class url_list{
          private String url;//图片

          public String getUrl() {
              return url;
          }

          public void setUrl(String url) {
              this.url = url;
          }
      }
  }

    public static final class user{
        private String avatar_url;//用户头像
        private String name;//用户名

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
