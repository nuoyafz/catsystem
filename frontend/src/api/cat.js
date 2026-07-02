import request from './request'

// 分页查询流浪猫
export const getCatPage = (params) => {
  return request({
    url: '/cat/page',
    method: 'get',
    params
  })
}

// 获取所有流浪猫
export const getCatList = () => {
  return request({
    url: '/cat/list',
    method: 'get'
  })
}

// 根据ID获取流浪猫
export const getCatById = (id) => {
  return request({
    url: `/cat/${id}`,
    method: 'get'
  })
}

// 添加流浪猫
export const addCat = (data) => {
  return request({
    url: '/cat',
    method: 'post',
    data
  })
}

// 更新流浪猫
export const updateCat = (data) => {
  return request({
    url: '/cat',
    method: 'put',
    data
  })
}

// 删除流浪猫
export const deleteCat = (id) => {
  return request({
    url: `/cat/${id}`,
    method: 'delete'
  })
}
