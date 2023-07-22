package com.driver;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class WhatsappService {
    WhatsappRepository whatsappRepository = new WhatsappRepository();
    public String createUser(String name, String mobile) {
        Optional<String> response = whatsappRepository.getMobile(mobile);
        if(response.isPresent()){
            throw new RuntimeException("User already exists");
        }
        return whatsappRepository.createUser(name,mobile);
    }

    public Group createGroup(List<User> users) {
        Group group;
        String nameOfGroup;
        if(users.size()>2){
            nameOfGroup = "Group "+whatsappRepository.getCustomGroupCount()+1;
        }
        else{
            nameOfGroup = users.get(1).getName();
        }
        group = whatsappRepository.createGroup(nameOfGroup, users);
        if(users.size()>2){
            whatsappRepository.createAdmin(group, users.get(0));
            // set group count
            whatsappRepository.setCustomGroupCount(whatsappRepository.getCustomGroupCount()+1);
        }
        else{
            whatsappRepository.createAdmin(group, users.get(1));
        }
        return group;
    }

    public int createMessage(String content) {
        int id = whatsappRepository.getMessageId()+1;
        whatsappRepository.createMessage(id,content);
        return id;
    }

    public int sendMessage(Message message, User sender, Group group) {
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        //If the message is sent successfully, return the final number of messages in that group.

        Optional<Group> groupResponse = whatsappRepository.getGroup(group.getName());
        if(groupResponse.isEmpty()){
            throw new RuntimeException("Group does not exist");
        }
        Optional<User> userResponse = whatsappRepository.getUserInGroup(sender,group);
        if(userResponse.isEmpty()){
            throw new RuntimeException("You are not allowed to send message");
        }
        return whatsappRepository.sendMessage(message, sender, group);
    }

    public String changeAdmin(User approver, User user, Group group) {
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the admin rights are transferred from approver to user.

        Optional<Group> groupResponse = whatsappRepository.getGroup(group.getName());
        if(groupResponse.isEmpty()){
            throw new RuntimeException("Group does not exist");
        }
        Optional<User> adminResponse = whatsappRepository.adminInThisGroup(approver, group);
        if(adminResponse.isEmpty()){
            throw new RuntimeException("Approver does not have rights");
        }
        Optional<User> userResponse = whatsappRepository.getUserInGroup(user,group);
        if(userResponse.isEmpty()){
            throw new RuntimeException("User is not a participant");
        }
        return whatsappRepository.changeAdmin(user,group);
    }

    public int removeUser(User user) {
        //This is a bonus problem and does not contains any marks
        //A user belongs to exactly one group
        //If user is not found in any group, throw "User not found" exception
        //If user is found in a group and it is the admin, throw "Cannot remove admin" exception
        //If user is not the admin, remove the user from the group, remove all its messages from all the databases, and update relevant attributes accordingly.
        //If user is removed successfully, return (the updated number of users in the group + the updated number of messages in group + the updated number of overall messages)
        return 0;
    }

    public String findMessage(Date start, Date end, int k) {
        //This is a bonus problem and does not contains any marks
        // Find the Kth latest message between start and end (excluding start and end)
        // If the number of messages between given time is less than K, throw "K is greater than the number of messages" exception

        return null;
    }
}