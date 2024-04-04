package hr.card.management.listener.api.service;

/**
 * Interface for API services.
 *
 * @param <T> the type of the entity
 * @param <S> the type of the entity's ID
 */
public interface ApiService<T, S> {

    T findById(S id);

    T save(T entity);

}
