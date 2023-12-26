package com.moing.backend.domain.block.domain.repository;

import com.moing.backend.domain.block.domain.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long>,BlockCustomRepository {

}
