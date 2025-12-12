import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import CabinsView from '../CabinsView.vue'
import axios from 'axios'

vi.mock('axios')
const mockedAxios = axios as any

describe('CabinsView', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders properly', () => {
    const wrapper = mount(CabinsView)
    expect(wrapper.text()).toContain('Cabin Attributions')
    expect(wrapper.text()).toContain('Get Cabin Attributions')
  })

  it('displays table when cabins are present', async () => {
    const mockCabins = [
      { cabinId: 'A1', category: 'STANDARD', bookingId: 'booking1' },
      { cabinId: 'A2', category: 'STANDARD', bookingId: 'booking2' }
    ]
    
    mockedAxios.get.mockResolvedValue({
      data: { cabins: mockCabins }
    })

    const wrapper = mount(CabinsView)
    await wrapper.find('[data-cy="fetch-cabin-attribution"]').trigger('click')
    await wrapper.vm.$nextTick()

    expect(wrapper.find('table').exists()).toBe(true)
    expect(wrapper.findAll('.cabin-row')).toHaveLength(2)
    expect(wrapper.text()).toContain('A1')
    expect(wrapper.text()).toContain('A2')
  })

  it('does not display table when no cabins', async () => {
    mockedAxios.get.mockResolvedValue({
      data: { cabins: [] }
    })

    const wrapper = mount(CabinsView)
    await wrapper.find('[data-cy="fetch-cabin-attribution"]').trigger('click')
    await wrapper.vm.$nextTick()

    expect(wrapper.find('table').exists()).toBe(false)
  })

  it('allows selecting criteria', async () => {
    const wrapper = mount(CabinsView)
    const select = wrapper.find('select')
    
    await select.setValue('travelers')
    expect((wrapper.vm as any).criteria).toBe('travelers')
    
    await select.setValue('bookingDateTime')
    expect((wrapper.vm as any).criteria).toBe('bookingDateTime')
  })

  it('calls API with correct criteria parameter', async () => {
    mockedAxios.get.mockResolvedValue({
      data: { cabins: [] }
    })

    const wrapper = mount(CabinsView)
    await wrapper.find('select').setValue('travelers')
    await wrapper.find('[data-cy="fetch-cabin-attribution"]').trigger('click')
    await wrapper.vm.$nextTick()

    expect(mockedAxios.get).toHaveBeenCalledWith(
      '/cruises/JUPITER_MOONS_EXPLORATION_2085/cabins',
      expect.objectContaining({
        params: { criteria: 'travelers' }
      })
    )
  })
})

