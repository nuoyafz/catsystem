import request from './request'

export function getAllTags() {
  return request({
    url: '/cat-tag/list',
    method: 'get'
  })
}

export function getTagById(id) {
  return request({
    url: `/cat-tag/${id}`,
    method: 'get'
  })
}

export function addTag(data) {
  return request({
    url: '/cat-tag',
    method: 'post',
    data
  })
}

export function updateTag(data) {
  return request({
    url: '/cat-tag',
    method: 'put',
    data
  })
}

export function deleteTag(id) {
  return request({
    url: `/cat-tag/${id}`,
    method: 'delete'
  })
}
