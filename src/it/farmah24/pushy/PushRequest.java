package it.farmah24.pushy;

public class PushRequest 
{
    public Object   notification;
    public String   priority;
    public String[] registration_ids;

    public PushRequest(Object notification, String[] registrationIDs) {
        this.notification     = notification;
        this.priority 		  = "high";
        this.registration_ids = registrationIDs;
    }
}