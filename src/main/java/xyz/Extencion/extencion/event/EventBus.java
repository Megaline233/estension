package xyz.Extencion.extencion.event;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private static final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP = new HashMap<>();

    public static void register(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventTarget.class) && method.getParameterTypes().length == 1) {
                if (!method.canAccess(object)) {
                    method.setAccessible(true);
                }

                Class<?> paramType = method.getParameterTypes()[0];
                if (Event.class.isAssignableFrom(paramType)) {
                    @SuppressWarnings("unchecked")
                    Class<? extends Event> eventClass = (Class<? extends Event>) paramType;
                    EventTarget eventTarget = method.getAnnotation(EventTarget.class);
                    MethodData methodData = new MethodData(object, method, eventTarget.priority());
                    REGISTRY_MAP.computeIfAbsent(eventClass, k -> new CopyOnWriteArrayList<>()).add(methodData);
                    sortListeners(eventClass);
                }
            }
        }
    }

    public static void unregister(Object object) {
        REGISTRY_MAP.values().forEach(methodDataList ->
                methodDataList.removeIf(methodData -> methodData.source == object));
        REGISTRY_MAP.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }

    public static boolean call(Event event) {
        List<MethodData> dataList = REGISTRY_MAP.get(event.getClass());

        if (dataList != null) {
            for (MethodData data : dataList) {
                try {
                    data.target.invoke(data.source, event);
                    if (event.isCancelled()) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private static void sortListeners(Class<? extends Event> eventClass) {
        List<MethodData> methodDataList = REGISTRY_MAP.get(eventClass);
        if (methodDataList != null) {
            methodDataList.sort((data1, data2) -> data2.priority - data1.priority);
        }
    }

    private static class MethodData {
        private final Object source;
        private final Method target;
        private final int priority;

        public MethodData(Object source, Method target, int priority) {
            this.source = source;
            this.target = target;
            this.priority = priority;
        }
    }
}