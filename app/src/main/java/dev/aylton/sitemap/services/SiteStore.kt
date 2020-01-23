package dev.aylton.sitemap.services

import dev.aylton.sitemap.models.SiteModel

interface SiteStore {
    fun create(site: SiteModel)
    fun update(site: SiteModel)
    fun delete(site: SiteModel)
    fun fetchSites()
}