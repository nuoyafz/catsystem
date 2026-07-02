import request from './request'

// 获取所有活动
export const getActivityList = (params) => {
  return request({
    url: '/activity/list',
    method: 'get',
    params
  })
}

// 根据ID获取活动
export const getActivityById = (id) => {
  return request({
    url: `/activity/${id}`,
    method: 'get'
  })
}

// 添加活动
export const addActivity = (data) => {
  return request({
    url: '/activity',
    method: 'post',
    data
  })
}

// 更新活动
export const updateActivity = (data) => {
  return request({
    url: '/activity',
    method: 'put',
    data
  })
}

// 删除活动
export const deleteActivity = (id) => {
  return request({
    url: `/activity/${id}`,
    method: 'delete'
  })
}
