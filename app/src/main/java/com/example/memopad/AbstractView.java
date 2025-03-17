package com.example.memopad;

import java.beans.PropertyChangeEvent;

public interface AbstractView {
    public abstract void modelPropertyChange(final PropertyChangeEvent evt);

}