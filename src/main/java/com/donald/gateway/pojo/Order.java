package com.donald.gateway.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Order {

    private short msgLength;
    private char msgType;
    private long clOrdID;
    private short symbol;
    private long price;
    private boolean active = true;     // false if msg dropped by rules

    private final Object activeSync = new Object();

    public Order(short msgLength, char msgType, long clOrdID, short symbol, long price) {
        this.msgLength = msgLength;
        this.msgType = msgType;
        this.clOrdID = clOrdID;
        this.symbol = symbol;
        this.price = price;
    }

    public boolean isActive() {
        synchronized (activeSync) {
            return active;
        }
    }

    public void setActive(boolean active) {
        synchronized (activeSync) {
            this.active = active;
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "msgLength=" + msgLength +
                ", msgType=" + msgType +
                ", clOrdID=" + clOrdID +
                ", symbol=" + symbol +
                ", price=" + price +
                ", active=" + active +
                '}';
    }
}
