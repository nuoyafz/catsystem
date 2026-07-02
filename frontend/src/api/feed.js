import request from './request'

// 获取所有投喂记录
export const getFeedList = () => {
  return request({
    url: '/feed/list',
    method: 'get'
  })
}

// 根据ID获取投喂记录
export const getFeedById = (id) => {
  return request({
    url: `/feed/${id}`,
    method: 'get'
  })
}

// 添加投喂记录
export const addFeed = (data) => {
  return request({
    url: '/feed',
    method: 'post',
    data
  })
}

// 更新投喂记录
export const updateFeed = (data) => {
  return request({
    url: '/feed',
    method: 'put',
    data
  })
}

// 删除投喂记录
export const deleteFeed = (id) => {
  return request({
    url: `/feed/${id}`,
    method: 'delete'
  })
}

// 获取投喂统计
export const getFeedCount = () => {
  return request({
    url: '/feed/count',
    method: 'get'
  })
}
