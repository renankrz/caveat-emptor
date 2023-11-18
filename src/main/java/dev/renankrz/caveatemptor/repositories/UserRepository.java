package dev.renankrz.caveatemptor.repositories;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;

import dev.renankrz.caveatemptor.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Basics

    User findByUsername(String username);

    List<User> findAllByOrderByUsernameAsc();

    List<User> findByRegistrationDateBetween(LocalDate start, LocalDate end);

    List<User> findByUsernameAndEmail(String username, String email);

    List<User> findByUsernameOrEmail(String username, String email);

    List<User> findByUsernameIgnoreCase(String username);

    List<User> findByLevelOrderByUsernameDesc(int level);

    List<User> findByLevelGreaterThanEqual(int level);

    List<User> findByUsernameContaining(String text);

    List<User> findByUsernameLike(String text);

    List<User> findByUsernameStartingWith(String start);

    List<User> findByUsernameEndingWith(String end);

    List<User> findByActive(boolean active);

    List<User> findByRegistrationDateIn(Collection<LocalDate> dates);

    List<User> findByRegistrationDateNotIn(Collection<LocalDate> dates);

    // Paging and sorting

    User findFirstByOrderByUsernameAsc();

    User findTopByOrderByRegistrationDateDesc();

    @Override
    Page<User> findAll(Pageable pageable);

    List<User> findFirst2ByLevel(int level, Sort sort);

    List<User> findByLevel(int level, Sort sort);

    List<User> findByActive(boolean active, Pageable pageable);

    // Streamable

    Streamable<User> findByEmailContaining(String text);

    Streamable<User> findByLevel(int level);

    // Query

    @Query("select count(u) from User u where u.active = ?1")
    int findNumberOfUsersByActive(boolean active);

    @Query("select u from User u where u.level = :level and u.active = :active")
    List<User> findByLevelAndActive(@Param("level") int level, @Param("active") boolean active);

    @Query(value = "SELECT COUNT(*) FROM users WHERE ACTIVE = ?1", nativeQuery = true)
    int findNumberOfUsersByActiveNative(boolean active);

    @Query("select u.username, LENGTH(u.email) as email_length from #{#entityName} u where u.username like %?1%")
    List<Object[]> findByAsArrayAndSort(String text, Sort sort);

}
