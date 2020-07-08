package org.yeffrey.cheesecakespring.library.core;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class RepositoryInMemory<T extends BaseDomain> {
    protected final ConcurrentHashMap<Long, T> db = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public T save(T entity) {
        if (entity.getId() == null) {
            Field f = ReflectionUtils.findField(entity.getClass(), "id");
            checkNotNull(f);
            f.setAccessible(true);
            try {
                f.set(entity, sequence.getAndIncrement());
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }

        }
        db.put(entity.getId(), entity);
        return entity;
    }
}
