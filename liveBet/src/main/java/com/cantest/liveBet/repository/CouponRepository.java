package com.cantest.liveBet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cantest.liveBet.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

//	@Query("SELECT COUNT(c) FROM Coupon c JOIN c.matches m WHERE m.id = :matchId")
//    long countByMatchId(@Param("matchId") Long matchId);
}
