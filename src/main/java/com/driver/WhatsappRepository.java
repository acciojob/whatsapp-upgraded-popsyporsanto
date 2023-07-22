package com.driver;

import java.util.*;

public class WhatsappRepository {
    private HashMap<String, User> userMap;
    private HashMap<String, Group> groupMap;
    private HashMap<Integer, Message> messageMap;

    //by-default
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public int getCustomGroupCount() {
        return customGroupCount;
    }

    public void setCustomGroupCount(int customGroupCount) {
        this.customGroupCount = customGroupCount;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }


    public WhatsappRepository(){
        this.userMap = new HashMap<String, User>();
        this.groupMap =  new HashMap<String, Group>();
        this.messageMap = new HashMap<Integer, Message>();
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public Optional<User> getUser(String name){
        if(this.userMap.containsKey(name)){
            return Optional.of(this.userMap.get(name));
        }
        return Optional.empty();
    }
    public Optional<Group> getGroup(String name){
        if(this.groupMap.containsKey(name)){
            return Optional.of(this.groupMap.get(name));
        }
        return Optional.empty();
    }
    public Optional<String> getMobile(String mobile){
        if(this.userMobile.contains(mobile)){
            return Optional.of(mobile);
        }
        return Optional.empty();
    }
    public Optional<User> getUserInGroup(User sender, Group group) {
        List<User> users = this.groupUserMap.get(group);
        for(User user : users){
            if(user.getName().equals(sender.getName())){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
    public String createUser(String name, String mobile) {
        this.userMap.put(name, new User(name, mobile));
        this.userMobile.add(mobile);
        return "SUCCESS";
    }

    public Group createGroup(String nameOfGroup, List<User> users) {
        //create a group
        Group group = new Group(nameOfGroup, users.size());
        //create group map
        this.groupMap.put(nameOfGroup, group);
        // put in group-users map
        this.groupUserMap.put(group, users);
        return group;
    }

    public void createAdmin(Group group, User user) {
        // make admin
        this.adminMap.put(group, user);
    }

//    public void createMessage(int id, String content) {
//        this.messageId+=1;
//        messageMap.put(id, new Message(id, content));
//    }

    public int sendMessage(Message message, User sender, Group group) {
        if(!this.groupMessageMap.containsKey(group)){
            this.groupMessageMap.put(group, new ArrayList<>());
        }
        // map message with user (sender)
        this.senderMap.put(message,sender);
        // add message to group
        List<Message> messages = this.groupMessageMap.get(group);
        messages.add(message);
        this.groupMessageMap.put(group,messages);
        return messages.size();
    }


    public Optional<User> adminInThisGroup(User approver, Group group) {
        if(this.adminMap.get(group).equals(approver)){
            return Optional.of(approver);
        }
        return Optional.empty();
    }

    public String changeAdmin(User user, Group group) {
        this.adminMap.put(group,user);
        return "SUCCESS";
    }

    public void createMessage(int id, String content) {
        this.messageId+=1;
        messageMap.put(id, new Message(id, content));
    }
}