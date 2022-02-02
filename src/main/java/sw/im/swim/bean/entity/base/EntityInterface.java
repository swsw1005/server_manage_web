package sw.im.swim.bean.entity.base;

import java.io.Serializable;

public interface EntityInterface<T extends Serializable> {
    T entity2DTO();
}
