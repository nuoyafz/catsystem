import request from './request'

// 获取所有志愿者
export const getVolunteerList = () => {
  return request({
    url: '/volunteer/list',
    method: 'get'
  })
}

// 根据ID获取志愿者
export const getVolunteerById = (id) => {
  return request({
    url: `/volunteer/${id}`,
    method: 'get'
  })
}

// 添加志愿者
export const addVolunteer = (data) => {
  return request({
    url: '/volunteer',
    method: 'post',
    data
  })
}

// 更新志愿者
export const updateVolunteer = (data) => {
  return request({
    url: '/volunteer',
    method: 'put',
    data
  })
}

// 删除志愿者
export const deleteVolunteer = (id) => {
  return request({
    url: `/volunteer/${id}`,
    method: 'delete'
  })
}
