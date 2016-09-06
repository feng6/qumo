package com.example.lanouhn.qumo.constans;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/24.
 */
public class Zhibo {

    private  String preview;
    private  String viewers;//观看人数

    public String getViewers() {
        return viewers;
    }

    public void setViewers(String viewers) {
        this.viewers = viewers;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
//channel内部类
    private channel channel;


    public Zhibo.channel getChannel() {
        return channel;
    }

    public void setChannel(Zhibo.channel channel) {
        this.channel = channel;
    }

    public  static  final class channel{
        private int    id;
        private String name;
        private String avatar;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    //user内部类
    private user user;


    public Zhibo.user getUser() {
        return user;
    }

    public void setUser(Zhibo.user user) {
        this.user = user;
    }

    public  static  final  class  user{
        private String name;
        private String avatar;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
    //tag内部类
    private tag tag ;


    public Zhibo.tag getTag() {
        return tag;
    }

    public void setTag(Zhibo.tag tag) {
        this.tag = tag;
    }

    public  static  final  class tag {
        private String tag;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }
    //location内部类
    private location location;

    public Zhibo.location getLocation() {
        return location;
    }

    public void setLocation(Zhibo.location location) {
        this.location = location;
    }

    public static final class location{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
