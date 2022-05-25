package pao.persistence;

import pao.components.UserConsumer;
import pao.setup.IRepository;

public interface IConsumerRepository extends IRepository<Long, UserConsumer> {
    public void returnLastOrder();
}
