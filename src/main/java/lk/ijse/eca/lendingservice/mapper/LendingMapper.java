package lk.ijse.eca.lendingservice.mapper;

import lk.ijse.eca.lendingservice.dto.LendingDTO;
import lk.ijse.eca.lendingservice.entity.Lending;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface LendingMapper {

    // --------------------- Entity → DTO ---------------------
    LendingDTO toDto(Lending lending);

    // --------------------- DTO → Entity ---------------------
    @Mapping(target = "id", ignore = true) // Ignore id on creation, MongoDB will generate it
    Lending toEntity(LendingDTO dto);

    // --------------------- Update existing entity ---------------------
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true) // Do not update id
    void updateEntity(LendingDTO dto, @MappingTarget Lending lending);
}