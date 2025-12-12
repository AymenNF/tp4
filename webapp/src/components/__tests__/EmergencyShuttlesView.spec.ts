import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import EmergencyShuttlesView from '../EmergencyShuttlesView.vue'
import axios from 'axios'

vi.mock('axios')
const mockedAxios = axios as any

describe('EmergencyShuttlesView', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders properly', () => {
    const wrapper = mount(EmergencyShuttlesView)
    expect(wrapper.text()).toContain('Emergency Shuttles Manifest')
    expect(wrapper.text()).toContain('Get Manifest')
  })

  it('displays manifest when data is present', async () => {
    const mockManifest = {
      cost: 200000,
      emergencyShuttles: [
        {
          type: 'RESCUE_SHIP',
          travelers: [
            { travelerId: 'traveler-1', travelerName: 'John Doe' }
          ],
          crewMembers: [
            { employeeId: 'ABC123', crewMemberName: 'Crew Member 1' }
          ]
        }
      ]
    }
    
    mockedAxios.get.mockResolvedValue({
      data: mockManifest
    })

    const wrapper = mount(EmergencyShuttlesView)
    await wrapper.find('[data-cy="fetch-emergency-shuttles-manifest"]').trigger('click')
    await wrapper.vm.$nextTick()

    expect(wrapper.find('#manifest-details').exists()).toBe(true)
    expect(wrapper.find('[data-cy="total-cost"]').text()).toContain('200000')
    expect(wrapper.text()).toContain('RESCUE_SHIP')
  })

  it('does not display table when manifest is empty', async () => {
    mockedAxios.get.mockResolvedValue({
      data: {
        cost: 0,
        emergencyShuttles: []
      }
    })

    const wrapper = mount(EmergencyShuttlesView)
    await wrapper.find('[data-cy="fetch-emergency-shuttles-manifest"]').trigger('click')
    await wrapper.vm.$nextTick()

    expect(wrapper.find('#manifest-details').exists()).toBe(false)
  })

  it('makes button red when manifest is empty', async () => {
    mockedAxios.get.mockResolvedValue({
      data: {
        cost: 0,
        emergencyShuttles: []
      }
    })

    const wrapper = mount(EmergencyShuttlesView)
    const button = wrapper.find('[data-cy="fetch-emergency-shuttles-manifest"]')
    
    expect(button.classes()).not.toContain('red-button')
    
    await button.trigger('click')
    await wrapper.vm.$nextTick()

    expect(button.classes()).toContain('red-button')
  })

  it('displays total cost correctly', async () => {
    const mockManifest = {
      cost: 150000,
      emergencyShuttles: [
        {
          type: 'RESCUE_SHIP',
          travelers: [],
          crewMembers: []
        }
      ]
    }
    
    mockedAxios.get.mockResolvedValue({
      data: mockManifest
    })

    const wrapper = mount(EmergencyShuttlesView)
    await wrapper.find('[data-cy="fetch-emergency-shuttles-manifest"]').trigger('click')
    await wrapper.vm.$nextTick()

    expect(wrapper.find('[data-cy="total-cost"]').text()).toContain('150000')
  })
})

