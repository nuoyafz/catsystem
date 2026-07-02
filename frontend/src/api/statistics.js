import request from './request'

export function getFeedTrend(days = 7) {
  return request({
    url: '/statistics/feed-trend',
    method: 'get',
    params: { days }
  })
}

export function getCatRanking(limit = 5) {
  return request({
    url: '/statistics/cat-ranking',
    method: 'get',
    params: { limit }
  })
}

export function getVolunteerRanking(limit = 5) {
  return request({
    url: '/statistics/volunteer-ranking',
    method: 'get',
    params: { limit }
  })
}

export function getLocationDistribution() {
  return request({
    url: '/statistics/location-distribution',
    method: 'get'
  })
}

export function getOverview() {
  return request({
    url: '/statistics/overview',
    method: 'get'
  })
}
