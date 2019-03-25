package com.turvo.bankingqueue.helper;

import java.util.PriorityQueue;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.turvo.bankingqueue.constant.CounterType;
import com.turvo.bankingqueue.constant.ServicePriority;
import com.turvo.bankingqueue.entity.Token;
import com.turvo.bankingqueue.exception.EmptyCounterQueueException;

public class CounterQueue {
    private PriorityQueue<Token> regularQueue = new PriorityQueue<Token>();
    private PriorityQueue<Token> premiumQueue = new PriorityQueue<Token>();
    private final CounterType counterQueueType;
    private int premiumQueueServeRatio;
    @SuppressWarnings("unused")
	private int regularQueueServeRatio;
    @SuppressWarnings("rawtypes")
	private CircularFifoQueue recentServedServiceList;
    private String serveRatio = "2:1";//TODO: read serveRatio from application properties

    @SuppressWarnings("unused")
	@Autowired
    private Environment env;
    
    public CounterQueue(CounterType counterQueueType) {
        this.counterQueueType = counterQueueType;

        if(counterQueueType.equals(CounterType.BOTH)) {
            if (serveRatio == null || serveRatio.isEmpty()) {
                throw new RuntimeException("Should pass serveRation for CounterType.BOTH in regular:premium format ex:1:2");
            } else if(serveRatio.split(":").length==2) {
                regularQueueServeRatio = Integer.valueOf(serveRatio.split(":")[1]);
                premiumQueueServeRatio = Integer.valueOf(serveRatio.split(":")[0]);
                recentServedServiceList = new CircularFifoQueue<ServicePriority>(premiumQueueServeRatio);
            } else {
                throw new RuntimeException("regular, premium serve ration not defined properly, correct format regular:premium ex: 1:2");
            }
        }
    }

    public Token addTokenToQueue(Token token) {
        token.setCounterAddedTime(DateTime.now());
        if(counterQueueType.equals(CounterType.PREMIUM)
               && token.getTokenPriority().equals(ServicePriority.REGULAR.name())) {
            throw new RuntimeException("This is PREMIUM counter, REGULAR customers will not be served here");

        } else {
            if(token.getTokenPriority().equals(ServicePriority.REGULAR.name())) {
                regularQueue.add(token);
            } else {
                premiumQueue.add(token);
            }
        }
        return token;
    }

    @SuppressWarnings("unchecked")
	public void addToRecentServedList(Token servedToken) {
        if(regularQueue.contains(servedToken)) {
            regularQueue.poll();
        } else if(premiumQueue.contains(servedToken)) {
            premiumQueue.poll();
        }

        if(counterQueueType.equals(CounterType.BOTH)) {
            recentServedServiceList.add(servedToken.getTokenPriority());
        }
    }

    public Token fetchToken() throws EmptyCounterQueueException {

        if(premiumQueue.isEmpty() && regularQueue.isEmpty()) {
            throw new EmptyCounterQueueException();
        }

        switch (counterQueueType) {
        case PREMIUM:
            return premiumQueue.peek();
        case REGULAR:
            return regularQueue.peek();
        case BOTH:
            return fetchTokenFromBothCounter();
        default:
            throw new RuntimeException("unknown counterType:"+counterQueueType);

        }
    }

    private Token fetchTokenFromBothCounter() throws EmptyCounterQueueException {
        if( premiumQueue.size()>0 &&
            (recentServedServiceList.isEmpty()
             || recentServedServiceList.size() != premiumQueueServeRatio
             ||recentServedServiceList.contains(ServicePriority.REGULAR))) {
            return premiumQueue.peek();
        } else if(regularQueue.size()>0) {
            return regularQueue.peek();
        } else {
            throw new EmptyCounterQueueException();
        }
    }

    public Integer getQueueLength() {
        return regularQueue.size() + premiumQueue.size();
    }


    public Integer getMinQueueLength(String priority) {
        if(priority == ServicePriority.REGULAR.name()
               || counterQueueType == CounterType.PREMIUM
               || counterQueueType == CounterType.REGULAR ) {
            return getQueueLength();
        } else {

            int minQueueForPremium = (premiumQueue.size()>0 ? premiumQueue.size()/premiumQueueServeRatio : 0);
            if(minQueueForPremium >= regularQueue.size()) {
                minQueueForPremium = minQueueForPremium + (minQueueForPremium - regularQueue.size());

            }
            return minQueueForPremium;
        }
    }
}
