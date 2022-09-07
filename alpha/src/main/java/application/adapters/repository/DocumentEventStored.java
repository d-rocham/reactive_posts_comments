package application.adapters.repository;

import application.generic.models.StoredEvent;

public class DocumentEventStored {

    private String id;
    private String aggregateRoodId;
    private StoredEvent storedEvent;

    public DocumentEventStored() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAggregateRoodId() {
        return aggregateRoodId;
    }

    public void setAggregateRoodId(String aggregateRoodId) {
        this.aggregateRoodId = aggregateRoodId;
    }

    public StoredEvent getStoredEvent() {
        return storedEvent;
    }

    public void setStoredEvent(StoredEvent storedEvent) {
        this.storedEvent = storedEvent;
    }
}
