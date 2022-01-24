package ru.softdarom.qrcheck.qrcode.handler.model.base

import com.fasterxml.jackson.annotation.JsonValue
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import ru.softdarom.qrcheck.qrcode.handler.exception.NotFoundException
import java.util.*
import java.util.stream.Collectors

enum class RoleType(@JsonValue val role: String) {

    USER(Ability.USER),
    CHECKMAN(Ability.CHECKMAN),
    PROMOTER(Ability.PROMOTER),
    API_KEY(Ability.API_KEY);

    companion object {

        private val logger = LoggerFactory.getLogger("UTIL")

        private val PREFIX = "ROLE_"

        fun roleOf(role: String?): RoleType {
            return EnumSet.allOf(RoleType::class.java)
                .stream()
                .filter { it.role == formatStringRole(role) }
                .findAny()
                .orElseThrow({ NotFoundException("Not found role $role") })
        }

        fun rolesOfAuthorities(): Set<RoleType?> {
            val authentication = SecurityContextHolder.getContext().authentication
            return authentication.authorities
                .stream()
                .map { obj: GrantedAuthority -> obj.authority }
                .filter({ it.contains(PREFIX) })
                .map { role: String? -> roleOf(role!!) }
                .collect(Collectors.toSet())
        }

        fun getMobileAbilityRoles(): Array<String> {
            return setOf(USER, CHECKMAN, PROMOTER)
                .map { it.role }
                .toTypedArray()
        }

        fun getInternalAbilityRoles(): Array<String> {
            return setOf(API_KEY)
                .map { it.role }
                .toTypedArray()
        }

        private fun formatStringRole(role: String?): String? {
            if (Objects.isNull(role)) {
                logger.debug("The passed role is null. Return empty string.")
                return ""
            }

            return if (role!!.startsWith(PREFIX)) role.replace(PREFIX, "") else role
        }
    }

    object Ability {
        const val USER = "USER"
        const val CHECKMAN = "CHECKMAN"
        const val PROMOTER = "PROMOTER"
        const val API_KEY = "API_KEY"
    }
}

