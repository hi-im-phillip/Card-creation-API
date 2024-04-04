package hr.card.management.listener.api.service;

/**
 * Interface for API services.
 *
 * @param <T> the type of the entity
 * @param <S> the type of the entity's ID
 * @param <I> the type of the entity to save
 */
public interface ApiService<T, S, I> {

    T findById(S id);

    T save(I entity);

}
