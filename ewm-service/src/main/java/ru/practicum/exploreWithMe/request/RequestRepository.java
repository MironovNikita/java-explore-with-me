package ru.practicum.exploreWithMe.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT count(r) FROM Request AS r " +
            "WHERE r.event.id = :eventId AND r.status = 'CONFIRMED'")
    Long findQuantityOfEventConfirmedRequests(@Param("eventId") Long eventId);

    @Query("SELECT r FROM Request AS r " +
            "WHERE r.event.id IN :eventIds AND r.status = 'CONFIRMED'")
    List<Request> findAllConfirmedRequestsByEventIdIn(List<Long> eventIds);

    @Query("SELECT r FROM Request AS r " +
            "WHERE r.event.id = :eventId AND r.event.initiator.id = :initiatorId AND " +
            "r.id in :requestIds " +
            "ORDER BY r.created ASC")
    List<Request> findRequestsForUpdate(@Param("eventId") Long eventId,
                                        @Param("initiatorId") Long initiatorId,
                                        @Param("requestIds") List<Long> requestIds);

    List<Request> findAllByRequesterId(Long requesterId);

    Optional<Request> findByRequesterIdAndEventId(Long requesterId, Long eventId);

    List<Request> findAllByEventIdAndEventInitiatorId(Long eventId, Long initiatorId);
}
