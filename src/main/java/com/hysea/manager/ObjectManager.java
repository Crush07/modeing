package com.hysea.manager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对象管理器，用于管理3D场景中的对象
 */
public class ObjectManager {
    private static ObjectManager instance;
    private List<SceneObject> objects;
    private List<ObjectManagerListener> listeners;
    private AtomicInteger objectCounter;
    
    private ObjectManager() {
        objects = new ArrayList<>();
        listeners = new ArrayList<>();
        objectCounter = new AtomicInteger(1);
    }
    
    public static ObjectManager getInstance() {
        if (instance == null) {
            instance = new ObjectManager();
        }
        return instance;
    }
    
    /**
     * 添加对象到管理器
     */
    public void addObject(String type, String name, Color color) {
        if (name == null || name.trim().isEmpty()) {
            name = type + "_" + String.format("%03d", objectCounter.getAndIncrement());
        }
        
        SceneObject obj = new SceneObject(name, type, color);
        objects.add(obj);
        
        // 通知监听器
        notifyObjectAdded(obj);
    }
    
    /**
     * 移除对象
     */
    public void removeObject(String name) {
        SceneObject toRemove = null;
        for (SceneObject obj : objects) {
            if (obj.getName().equals(name)) {
                toRemove = obj;
                break;
            }
        }
        
        if (toRemove != null) {
            objects.remove(toRemove);
            notifyObjectRemoved(toRemove);
        }
    }
    
    /**
     * 获取所有对象
     */
    public List<SceneObject> getAllObjects() {
        return new ArrayList<>(objects);
    }
    
    /**
     * 添加监听器
     */
    public void addListener(ObjectManagerListener listener) {
        listeners.add(listener);
    }
    
    /**
     * 移除监听器
     */
    public void removeListener(ObjectManagerListener listener) {
        listeners.remove(listener);
    }
    
    private void notifyObjectAdded(SceneObject obj) {
        for (ObjectManagerListener listener : listeners) {
            listener.onObjectAdded(obj);
        }
    }
    
    private void notifyObjectRemoved(SceneObject obj) {
        for (ObjectManagerListener listener : listeners) {
            listener.onObjectRemoved(obj);
        }
    }
    
    /**
     * 清空所有对象
     */
    public void clear() {
        List<SceneObject> objectsToRemove = new ArrayList<>(objects);
        objects.clear();
        
        for (SceneObject obj : objectsToRemove) {
            notifyObjectRemoved(obj);
        }
    }
    
    /**
     * 场景对象类
     */
    public static class SceneObject {
        private String name;
        private String type;
        private Color color;
        private boolean visible;
        
        public SceneObject(String name, String type, Color color) {
            this.name = name;
            this.type = type;
            this.color = color;
            this.visible = true;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getType() {
            return type;
        }
        
        public Color getColor() {
            return color;
        }
        
        public void setColor(Color color) {
            this.color = color;
        }
        
        public boolean isVisible() {
            return visible;
        }
        
        public void setVisible(boolean visible) {
            this.visible = visible;
        }
    }
    
    /**
     * 对象管理器监听器接口
     */
    public interface ObjectManagerListener {
        void onObjectAdded(SceneObject obj);
        void onObjectRemoved(SceneObject obj);
    }
}