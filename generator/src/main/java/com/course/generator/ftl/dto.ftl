package com.course.service.domain.dto;

<#list typeSet as type>
<#if type=='Date'>
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
</#if>
<#if type=='BigDecimal'>
import java.math.BigDecimal;
</#if>
</#list>
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ${Domain}Dto {

    <#list fieldList as field>
    /**
     * ${field.comment}
     */
    <#if field.javaType=='Date'>
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    </#if>
    private ${field.javaType} ${field.nameHump};

    </#list>


}