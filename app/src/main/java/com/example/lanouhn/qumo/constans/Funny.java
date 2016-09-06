package com.example.lanouhn.qumo.constans;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/18.
 * 好笑不 实体类
 */
public class Funny {


  private String content;//标题

    private user user;

    public Funny.user getUser() {
        return user;
    }

    public void setUser(Funny.user user) {
        this.user = user;
    }

    private large_image large_image;//图片

    public Funny.large_image getLarge_image() {
        return large_image;
    }

    public void setLarge_image(Funny.large_image large_image) {
        this.large_image = large_image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

  public  static final class large_image{
     private List<list_url> list_url;
      private String url;//图片

      public String getUrl() {
          return url;
      }

      public void setUrl(String url) {
          this.url = url;
      }

      public List<Funny.large_image.list_url> getList_url() {
          return list_url;
      }

      public void setList_url(List<Funny.large_image.list_url> list_url) {
          this.list_url = list_url;
      }

      public static  final class list_url{
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
