import request from './request'

export function getAllAdoptions() {
  return request({
    url: '/adoption/list',
    method: 'get'
  })
}

export function getAdoptionById(id) {
  return request({
    url: `/adoption/${id}`,
    method: 'get'
  })
}

export function applyAdoption(data) {
  return request({
    url: '/adoption',
    method: 'post',
    data
  })
}

export function updateAdoption(data) {
  return request({
    url: '/adoption',
    method: 'put',
    data
  })
}

export function deleteAdoption(id) {
  return request({
    url: `/adoption/${id}`,
    method: 'delete'
  })
}

export function reviewAdoption(id, data) {
  return request({
    url: `/adoption/review/${id}`,
    method: 'put',
    data
  })
}
