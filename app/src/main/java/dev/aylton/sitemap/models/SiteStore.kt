package dev.aylton.sitemap.models

interface SiteStore {
    fun create(site: SiteModel)
    fun update(site: SiteModel)
    fun delete(site: SiteModel)
    fun fetchSites(callback: () -> Unit, isPublic: Boolean)
}